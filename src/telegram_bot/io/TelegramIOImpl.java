package telegram_bot.io;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import telegram_bot.bot.Bot;
import telegram_bot.bot.ChatId;

public class TelegramIOImpl implements TelegramIO {
    private Bot bot;

    public TelegramIOImpl (Bot bot) {
        this.bot = bot;
    }
    public void write(SendMessage sendMessage, ChatId chatId) throws TelegramApiException {
        sendMessage.setChatId(chatId.getChatId());
        bot.execute(sendMessage);
    }
}