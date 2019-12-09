package main.Logic;

import main.Message.MessageBroker;
import main.Message.Messages;
import main.Players.Player;
import main.Players.PlayerStorage;
import main.bot.ChatId;
import main.io.TelegramIOImpl;
import main.questions.QnA;
import main.questions.QuestionsProvider;

import java.io.IOException;
import java.util.List;

public class Logic  {
    private QuestionsProvider questionsProvider;
    private PlayerStorage playerStorage;
    private MessageBroker messageBroker;
    private TelegramIOImpl io;
    private HintsLogic hintsLogic;

    public Logic(QuestionsProvider questions, PlayerStorage playerStorage, MessageBroker messageBroker, TelegramIOImpl io, HintsLogic hintsLogic) {
        this.questionsProvider = questions;
        this.playerStorage = playerStorage;
        this.messageBroker = messageBroker;
        this.io = io;
        this.hintsLogic = hintsLogic;
    }

    public void processMessage(String text, ChatId chatId) throws Exception {
        switch (text) {
            case "/start":
                io.write(messageBroker.getMessage(Messages.START), chatId);
                break;
            case "/game":
                io.write(messageBroker.getAmountButtons(), chatId);
                break;
            case "/help":
                io.write(messageBroker.getMessage(Messages.HELP), chatId);
                break;
            case "/replaceQuestion":
                Player player1 = hintsLogic.replaceQuestion(chatId);
                io.write(messageBroker.getMessage(player1), chatId);
                break;
            case "/delete2IncorrectAnswers":
                Player player2 = hintsLogic.delete2IncorrectAnswers(chatId);
                io.write(messageBroker.getMessage(player2), chatId);
                break;
            case "/rightMakeMistake":
                Player player3 = hintsLogic.rightMakeMistake(chatId);
                io.write(messageBroker.getMessage(player3), chatId);
                break;
            case "/takeMoney":
                io.write(messageBroker.createMessage(takeMoney(chatId)), chatId);
                break;
            default:
                if (messageBroker.getAmount().contains(text)) {
                    Player player4 = setAmount(chatId, text);
                    io.write(messageBroker.getMessage(player4), chatId);
                } else {
                    Player player5 = playerStorage.getPlayer(chatId);
                    if (text.equals(player5.getCurrentQnA().getCorrectAnswer())) {
                        player5.addCountCorrectAnswers();
                        io.write(messageBroker.getMessage(Messages.CORRECT), chatId);
                        if (player5.getCountCorrectAnswers() == 15)
                            io.write(messageBroker.getMessage(Messages.CONGRATULATIONS), chatId);
                        else {
                            player5.setCurrentQnA(questionsProvider.getQuestion(getLevelDifficulty(player5.getCountCorrectAnswers() + 1)));
                            io.write(messageBroker.getMessage(player5), chatId);
                        }
                    } else {
                        io.write(messageBroker.getMessage(Messages.INCORRECT), chatId);
                        if (player5.getHints().getUsedRightMakeMistake() && player5.getHints().getRightMakeMistake()) {
                            Player player6 = rightMistake(player5, text);
                            io.write(messageBroker.getMessage(player6), chatId);
                        } else {
                            io.write(messageBroker.getMessage(Messages.GAMEOVER), chatId);
                            playerStorage.deletePlayer(chatId);
                        }
                    }
                }
                break;
        }
    }

    private int getLevelDifficulty(int correctAnswers) {
        if (correctAnswers > 4 && correctAnswers < 10)
            return 2;
        else if (correctAnswers >= 10)
            return 3;
        else
            return 4;
    }

    private Boolean takeMoney(Player player) {
        int a = player.getCountCorrectAnswers();
        for (int i = 0; i < messageBroker.getAmount().size(); i++) {
            if (messageBroker.getAmount().get(i).equals(player.getAmount()) && a >= i + 1)
                return true;
        }
        return false;
    }

    private String takeMoney(ChatId chatId) {
        Player player = playerStorage.getPlayer(chatId);
        if (takeMoney(player)) {
            playerStorage.deletePlayer(chatId);
            return String.format("Ваш выигрыш: %1s", player.getAmount());
        } else {
            return "Нечего забирать";
        }
    }

    private Player setAmount(ChatId chatId, String text) throws IOException {
        Player player = new Player(text);
        playerStorage.addPlayer(chatId, player);
        player.setCurrentQnA(questionsProvider.getQuestion(getLevelDifficulty(player.getCountCorrectAnswers())));
        return player;
    }

    private Player rightMistake(Player player, String text) {
        String question = player.getCurrentQnA().getQuestion();
        String rightAnswer = player.getCurrentQnA().getCorrectAnswer();
        List<String> answers = player.getCurrentQnA().getAnswers();
        answers.remove(text);
        player.setCurrentQnA(new QnA(question, answers, rightAnswer));
        player.getHints().setRightMakeMistake();
        return player;
    }
}
