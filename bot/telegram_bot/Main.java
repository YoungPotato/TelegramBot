package main_package;

import main_package.questions.QuestionsProvider;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {
    public static void main(String[] args) {
        String apiUrl = "https://engine.lifeis.porn/api/millionaire.php?q=%s";
        QuestionsProvider questions = new QuestionsProvider(apiUrl);
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot(questions));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}