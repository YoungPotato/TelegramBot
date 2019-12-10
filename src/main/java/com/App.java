package com;

import Message.Amount;
import Message.Buttons;
import Message.MessageFactory;
import bot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import Logic.HintsLogic;
import Logic.Logic;
import Players.PlayerStorage;
import PublisherSubscriber.Subscriber;
import io.FileReaderImpl;
import io.TelegramIOImpl;
import questions.HttpRequest;
import questions.JsonParser;
import questions.QuestionsProvider;

public class App {
    public static void main(String[] args) {
        String apiUrl = "https://engine.lifeis.porn/api/millionaire.php?q=%s";

        HttpRequest httpRequest = new HttpRequest();
        JsonParser jsonParser = new JsonParser();
        QuestionsProvider questionsProvider = new QuestionsProvider(apiUrl, httpRequest, jsonParser);

        Buttons buttons = new Buttons();
        MessageFactory messageFactory = new MessageFactory(buttons);
        PlayerStorage playerStorage = new PlayerStorage();
        HintsLogic hintsLogic = new HintsLogic(playerStorage, questionsProvider);

        String botUserName = System.getenv("botUserName");
        String botToken = System.getenv("botToken");

        ApiContextInitializer.init();
        Bot bot = new Bot(botUserName, botToken);
        TelegramIOImpl io = new TelegramIOImpl(bot);

        Amount amount = new Amount();
        Logic logic = new Logic(questionsProvider, playerStorage, messageFactory, io, hintsLogic, amount);
        Subscriber subscriber = new Subscriber(logic);

        bot.setSubscriber(subscriber);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}