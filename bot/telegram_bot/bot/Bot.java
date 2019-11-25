package telegram_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Bot extends TelegramLongPollingBot {

    private Subscriber subscriber;

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
        }
        else if (update.hasCallbackQuery()) {
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
        String botUserName = "";
        try {
            botUserName= Files.readAllLines(Paths.get("./bot/telegram_bot/privacy_information/bot_user_name.txt"), StandardCharsets.UTF_8).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return botUserName;
    }

    @Override
    public String getBotToken() {
        String botToken = "";
        try {
            botToken = Files.readAllLines(Paths.get("./bot/telegram_bot/privacy_information/token.txt"), StandardCharsets.UTF_8).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return botToken;
    }
}
