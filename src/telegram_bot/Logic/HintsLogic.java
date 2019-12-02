package telegram_bot.Logic;

import telegram_bot.Players.Player;
import telegram_bot.Players.PlayerStorage;
import telegram_bot.bot.ChatId;
import telegram_bot.questions.QnA;
import telegram_bot.questions.QuestionsProvider;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class HintsLogic {
    private PlayerStorage playerStorage;
    private QuestionsProvider questionsProvider;

    public HintsLogic(PlayerStorage playerStorage, QuestionsProvider questionsProvider) {
        this.playerStorage = playerStorage;
        this.questionsProvider = questionsProvider;
    }
    public Player delete2IncorrectAnswers(ChatId chatId) {
        Player player = playerStorage.getPlayer(chatId);
        if (player.getHints().getDelete2IncorrectAnswers()) {
            String question = player.getCurrentQnA().getQuestion();
            String rightAnswer = player.getCurrentQnA().getCorrectAnswer();
            List<String> answers = player.getCurrentQnA().getAnswers();
            Random rnd = new Random();
            while (answers.size() != 2) {
                int i = rnd.nextInt(answers.size());
                if (!answers.get(i).equals(rightAnswer))
                    answers.remove(i);
            }
            player.setCurrentQnA(new QnA(question, answers, rightAnswer));
            player.getHints().setDelete2IncorrectAnswers();
        }
        return player;
    }

    public Player replaceQuestion(ChatId chatId) throws IOException {
        Player player = playerStorage.getPlayer(chatId);
        if (player.getHints().getReplaceQuestion()) {
            player.setCurrentQnA(questionsProvider.getQuestion(getLevelDifficulty(player.getCountCorrectAnswers() + 1)));
            player.getHints().setReplaceQuestion();
        }
        return player;
    }

    public Player rightMakeMistake(ChatId chatId) {
        Player player = playerStorage.getPlayer(chatId);
        if (player.getHints().getRightMakeMistake()) {
            player.getHints().setUsedRightMakeMistake();
        }
        return player;
    }

    public int getLevelDifficulty(int correctAnswers) {
        if (correctAnswers > 4 && correctAnswers < 10)
            return 2;
        else if (correctAnswers >= 10)
            return 3;
        else
            return 4;
    }
}