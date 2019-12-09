package io;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import bot.Bot;
import bot.ChatId;

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