package com;

import Message.Amount;
import Message.Buttons;
import Message.MessageFactory;
import bot.Bot;
import database.DatabaseOperation;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import Logic.Logic;
import Players.PlayerStorage;
import PublisherSubscriber.Subscriber;
import io.TelegramIOImpl;
import questions.HttpRequest;
import questions.JsonParser;
import questions.QuestionsProvider;

import java.sql.SQLException;


public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String apiUrl = "https://engine.lifeis.porn/api/millionaire.php?q=%s";

        HttpRequest httpRequest = new HttpRequest();
        JsonParser jsonParser = new JsonParser();
        QuestionsProvider questionsProvider = new QuestionsProvider(apiUrl, httpRequest, jsonParser);

        Buttons buttons = new Buttons();
        MessageFactory messageFactory = new MessageFactory(buttons);
        PlayerStorage playerStorage = new PlayerStorage();

//        String botUserName = System.getenv("botUserName");
//        String botToken = System.getenv("botToken");

        String botUserName = "Who_Millionaire_Bot";
        String botToken = "1008542793:AAEm5Zh5ShgwwfDVCqU5kC-7sCCTF-2Nf1I";

        ApiContextInitializer.init();
        Bot bot = new Bot(botUserName, botToken);
        TelegramIOImpl io = new TelegramIOImpl(bot);

        DatabaseOperation databaseOperation = new DatabaseOperation();
        Amount amount = new Amount();
        Logic logic = new Logic(questionsProvider, playerStorage, messageFactory, io, amount, databaseOperation);
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