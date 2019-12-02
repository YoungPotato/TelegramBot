package telegram_bot.Players;

import telegram_bot.Hints.Hints;
import telegram_bot.questions.QnA;

public class Player {
    private int countCorrectAnswers;
    private QnA currentQnA;
    private String amount;
    private Hints hints;

    public Player(String amount) {
        this.countCorrectAnswers = 0;
        this.currentQnA = null;
        this.amount = amount;
        hints = new Hints();
    }

    public String getAmount() {
        return amount;
    }

    public Hints getHints() {
        return hints;
    }

    public void addCountCorrectAnswers() {
        this.countCorrectAnswers += 1;
    }

    public int getCountCorrectAnswers() {
        return countCorrectAnswers;
    }

    public void setCurrentQnA(QnA qnA) {
        this.currentQnA = qnA;
    }

    public QnA getCurrentQnA() {
        return currentQnA;
    }
}
