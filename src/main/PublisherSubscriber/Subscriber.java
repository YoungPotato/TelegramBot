package main.PublisherSubscriber;

import main.Logic.Logic;
import main.bot.ChatId;

public class Subscriber {

    private Logic logic;

    public Subscriber(Logic logic) {
        this.logic = logic;
    }

    public void sendMessage(String text, ChatId chatId) throws Exception {
        logic.processMessage(text, chatId);
    }
}