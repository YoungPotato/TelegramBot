package Logic;

import Message.Amount;
import Message.MessageFactory;
import Message.Messages;
import Players.Player;
import Players.PlayerStorage;
import bot.ChatId;
import io.TelegramIOImpl;
import questions.QnA;
import questions.QuestionsProvider;

import java.io.IOException;
import java.util.List;

public class Logic  {
    private QuestionsProvider questionsProvider;
    private PlayerStorage playerStorage;
    private MessageFactory messageFactory;
    private TelegramIOImpl io;
    private HintsLogic hintsLogic;
    private Amount amount;

    public Logic(QuestionsProvider questions, PlayerStorage playerStorage, MessageFactory messageFactory, TelegramIOImpl io, HintsLogic hintsLogic, Amount amount) {
        this.questionsProvider = questions;
        this.playerStorage = playerStorage;
        this.messageFactory = messageFactory;
        this.io = io;
        this.hintsLogic = hintsLogic;
        this.amount = amount;
    }

    public void processMessage(String text, ChatId chatId) throws Exception {
        switch (text) {
            case "/start":
                io.write(messageFactory.getMessage(Messages.START), chatId);
                break;
            case "/game":
                io.write(messageFactory.getAmountButtons(amount.getStrAmount()), chatId);
                break;
            case "/help":
                io.write(messageFactory.getMessage(Messages.HELP), chatId);
                break;
            case "/replaceQuestion":
                Player player1 = hintsLogic.replaceQuestion(chatId);
                io.write(messageFactory.getMessage(player1.getCurrentQnA(), player1.getHints(), player1.getCountCorrectAnswers()), chatId);
                break;
            case "/delete2IncorrectAnswers":
                Player player2 = hintsLogic.delete2IncorrectAnswers(chatId);
                io.write(messageFactory.getMessage(player2.getCurrentQnA(), player2.getHints(), player2.getCountCorrectAnswers()), chatId);
                break;
            case "/rightMakeMistake":
                Player player3 = hintsLogic.rightMakeMistake(chatId);
                io.write(messageFactory.getMessage(player3.getCurrentQnA(), player3.getHints(), player3.getCountCorrectAnswers()), chatId);
                break;
            case "/takeMoney":
                io.write(messageFactory.createSimpleMessage(takeMoney(chatId)), chatId);
                break;
            default:
                if (amount.getStrAmount().contains(text)) {
                    Player player4 = setAmount(chatId, text);
                    io.write(messageFactory.getMessage(player4.getCurrentQnA(), player4.getHints(), player4.getCountCorrectAnswers()), chatId);
                } else {
                    Player player5 = playerStorage.getPlayer(chatId);
                    if (text.equals(player5.getCurrentQnA().getCorrectAnswer())) {
                        player5.addCountCorrectAnswers();
                        io.write(messageFactory.getMessage(Messages.CORRECT), chatId);
                        if (player5.getCountCorrectAnswers() == 15)
                            io.write(messageFactory.getMessage(Messages.CONGRATULATIONS), chatId);
                        else {
                            player5.setCurrentQnA(questionsProvider.getQuestion(hintsLogic.getLevelDifficulty(player5.getCountCorrectAnswers() + 1)));
                            io.write(messageFactory.getMessage(player5.getCurrentQnA(), player5.getHints(), player5.getCountCorrectAnswers()), chatId);
                        }
                    } else {
                        io.write(messageFactory.getMessage(Messages.INCORRECT), chatId);
                        if (player5.getHints().getUsedRightMakeMistake() && player5.getHints().getRightMakeMistake()) {
                            Player player6 = rightMistake(player5, text);
                            io.write(messageFactory.getMessage(player6.getCurrentQnA(), player6.getHints(), player6.getCountCorrectAnswers()), chatId);
                        } else {
                            io.write(messageFactory.getMessage(Messages.GAMEOVER), chatId);
                            playerStorage.deletePlayer(chatId);
                        }
                    }
                }
                break;
        }
    }

    private Boolean takeMoney(Player player) {
        int a = player.getCountCorrectAnswers();
        for (int i = 0; i < amount.getAmount().size(); i++) {
            if (amount.getAmount().get(i).equals(player.getAmount()) && a >= i + 1)
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
        Player player = new Player(Integer.parseInt(text));
        playerStorage.addPlayer(chatId, player);
        player.setCurrentQnA(questionsProvider.getQuestion(hintsLogic.getLevelDifficulty(player.getCountCorrectAnswers())));
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
