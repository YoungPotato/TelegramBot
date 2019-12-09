package PublisherSubscriber;

import Logic.Logic;
import bot.ChatId;

public class Subscriber {

    private Logic logic;

    public Subscriber(Logic logic) {
        this.logic = logic;
    }

    public void sendMessage(String text, ChatId chatId) throws Exception {
        logic.processMessage(text, chatId);
    }
}