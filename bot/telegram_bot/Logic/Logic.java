package telegram_bot;

import telegram_bot.Message.MessageBroker;
import telegram_bot.Message.Messages;
import telegram_bot.io.TelegramIOImpl;
import telegram_bot.questions.QnA;
import telegram_bot.questions.QuestionsProvider;

import java.util.List;
import java.util.Random;

public class Logic  {
    private QuestionsProvider questions;
    private PlayerStorage playerStorage;
    private MessageBroker messageBroker;
    private TelegramIOImpl io;

    public Logic(QuestionsProvider questions, PlayerStorage playerStorage, MessageBroker messageBroker, TelegramIOImpl io) {
        this.questions = questions;
        this.playerStorage = playerStorage;
        this.messageBroker = messageBroker;
        this.io = io;
    }

    public void processMessage(String text, ChatId chatId) throws Exception {
        switch (text) {
            case "/start":
                io.write(messageBroker.getMessage(Messages.START), chatId);
                break;
            case "/game":
                io.write(messageBroker.getAmount(), chatId);
                break;
            case "/help":
                io.write(messageBroker.getMessage(Messages.HELP), chatId);
                break;
            case "/replaceQuestion":
                Player player2 = playerStorage.getPlayer(chatId);
                if (player2.getHints().getReplaceQuestionValue()) {
                    player2.setCurrentQnA(questions.getQuestion(getLevelDifficulty(player2.getCountCorrectAnswers() + 1)));
                    player2.getHints().setReplaceQuestionValue();
                    io.write(messageBroker.getMessage(player2), chatId);
                }
                break;
            case "/delete2IncorrectAnswers":
                Player player3 = playerStorage.getPlayer(chatId);
                if (player3.getHints().getDelete2IncorrectAnswersValue()) {
                    String question = player3.getCurrentQnA().getQuestion();
                    String rightAnswer = player3.getCurrentQnA().getCorrectAnswer();
                    List<String> answers = player3.getCurrentQnA().getAnswers();
                    Random rnd = new Random();
                    while (answers.size() != 2) {
                        int i = rnd.nextInt(answers.size());
                        if (!answers.get(i).equals(rightAnswer))
                            answers.remove(i);
                    }
                    player3.setCurrentQnA(new QnA(question, answers, rightAnswer));
                    player3.getHints().setDelete2IncorrectAnswers();
                    io.write(messageBroker.getMessage(player3), chatId);
                }
                break;
            case "/rightMakeMistake":
                Player player4 = playerStorage.getPlayer(chatId);
                if (player4.getHints().getRightMakeMistakeValue()) {
                    player4.getHints().setUsedRightMakeMistake();
                    io.write(messageBroker.getMessage(player4), chatId);
                }
                break;
            case "/takeMoney":
                Player player5 = playerStorage.getPlayer(chatId);
                if (takeMoney(player5)) {
                    io.write(messageBroker.createMessage(String.format("Ваш выигрыш: %1s", player5.getAmount())), chatId);
                    playerStorage.deletePlayer(chatId);
                } else {
                    io.write(messageBroker.createMessage("Нечего забирать"), chatId);
                }
                break;
            default:
                if (messageBroker.amount.contains(text)) {
                    Player player = new Player(text);
                    playerStorage.addPlayer(chatId, player);
                    player.setCurrentQnA(questions.getQuestion(getLevelDifficulty(player.getCountCorrectAnswers())));
                    io.write(messageBroker.getMessage(player), chatId);
                    break;
                } else {
                    Player player1 = playerStorage.getPlayer(chatId);
                    if (text.equals(player1.getCurrentQnA().getCorrectAnswer())) {
                        player1.addCountCorrectAnswers();
                        io.write(messageBroker.getMessage(Messages.CORRECT), chatId);
                        if (player1.getCountCorrectAnswers() == 15)
                            io.write(messageBroker.getMessage(Messages.CONGRATULATIONS), chatId);
                        else {
                            player1.setCurrentQnA(questions.getQuestion(getLevelDifficulty(player1.getCountCorrectAnswers() + 1)));
                            io.write(messageBroker.getMessage(player1), chatId);
                        }
                    } else {
                        io.write(messageBroker.getMessage(Messages.INCORRECT), chatId);
                        if (player1.getHints().getUsedRightMakeMistake() && player1.getHints().getRightMakeMistakeValue()) {
                            String question = player1.getCurrentQnA().getQuestion();
                            String rightAnswer = player1.getCurrentQnA().getCorrectAnswer();
                            List<String> answers = player1.getCurrentQnA().getAnswers();
                            answers.remove(text);
                            player1.setCurrentQnA(new QnA(question, answers, rightAnswer));
                            player1.getHints().setRightMakeMistake();
                            io.write(messageBroker.getMessage(player1), chatId);
                        } else {
                            io.write(messageBroker.getMessage(Messages.GAMEOVER), chatId);
                            playerStorage.deletePlayer(chatId);
                        }
                    }
                    break;
                }
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
        for (int i = 0; i < messageBroker.amount.size(); i++) {
            if (messageBroker.amount.get(i).equals(player.getAmount()) && a >= i + 1)
                return true;
        }
        return false;
    }
}
