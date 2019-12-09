package main.com;

import main.bot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import main.Logic.HintsLogic;
import main.Logic.Logic;
import main.Message.MessageBroker;
import main.Players.PlayerStorage;
import main.PublisherSubscriber.Subscriber;
import main.io.FileReaderImpl;
import main.io.TelegramIOImpl;
import main.questions.HttpRequest;
import main.questions.JsonParser;
import main.questions.QuestionsProvider;

public class Main {
    public static void main(String[] args) {
        String apiUrl = "https://engine.lifeis.porn/api/millionaire.php?q=%s";
        String botUserNamePath = "src/telegram_bot/privacy_information/bot_user_name.txt";
        String botTokenPath = "src/telegram_bot/privacy_information/token.txt";

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
        Bot bot = new Bot(botUserName, botToken);
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