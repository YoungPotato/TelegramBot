package telegram_bot.io;

import telegram_bot.questions.QnA;

public interface IO {
    void write(Messages messages, String playerId);
    void write(QnA qnA);
}
