package telegram_bot.io;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram_bot.bot.ChatId;

public interface TelegramIO {
    void write(SendMessage sendMessage, ChatId chatId) throws TelegramApiException;
}
