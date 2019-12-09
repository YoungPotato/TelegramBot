package com;

import bot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import Logic.HintsLogic;
import Logic.Logic;
import Message.MessageBroker;
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
        String botUserNamePath = "/src/main/java/privacy_information/bot_user_name.txt";
        String botTokenPath = "/src/main/java/privacy_information/token.txt";

        HttpRequest httpRequest = new HttpRequest();
        JsonParser jsonParser = new JsonParser();
        QuestionsProvider questionsProvider = new QuestionsProvider(apiUrl, httpRequest, jsonParser);

        MessageBroker messageBroker = new MessageBroker();
        PlayerStorage playerStorage = new PlayerStorage();
        HintsLogic hintsLogic = new HintsLogic(playerStorage, questionsProvider);

        FileReaderImpl fileReader = new FileReaderImpl();
        String botUserName = fileReader.getInformation(botUserNamePath);
        String botToken = fileReader.getInformation(botTokenPath);

        ApiContextInitializer.init();
        Bot bot = new Bot("Who_Millionaire_Bot", "1008542793:AAEm5Zh5ShgwwfDVCqU5kC-7sCCTF-2Nf1I");
        TelegramIOImpl io = new TelegramIOImpl(bot);

        Logic logic = new Logic(questionsProvider, playerStorage, messageBroker, io, hintsLogic);
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