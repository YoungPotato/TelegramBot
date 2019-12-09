package main.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import main.PublisherSubscriber.Subscriber;

public class Bot extends TelegramLongPollingBot {
    private String userName;
    private String token;
    private Subscriber subscriber;

    public Bot(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            try {
                subscriber.sendMessage(message.getText(), new ChatId(message.getChatId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            ChatId chatId = new ChatId(update.getCallbackQuery().getMessage().getChatId());
            String text = update.getCallbackQuery().getData();
            try {
                subscriber.sendMessage(text, chatId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
