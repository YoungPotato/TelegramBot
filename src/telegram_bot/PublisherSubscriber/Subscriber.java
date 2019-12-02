package telegram_bot.PublisherSubscriber;

import telegram_bot.Logic.Logic;
import telegram_bot.bot.ChatId;

public class Subscriber {

    private Logic logic;

    public Subscriber(Logic logic) {
        this.logic = logic;
    }

    public void sendMessage(String text, ChatId chatId) throws Exception {
        logic.processMessage(text, chatId);
    }
}