package main_package;

import main_package.io.IOImpl;
import main_package.questions.Json;
import main_package.questions.QuestionsProvider;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String apiUrl = "https://engine.lifeis.porn/api/millionaire.php?q=%s";
        Json json = new Json();
        QuestionsProvider questions = new QuestionsProvider(apiUrl);
        IOImpl io = new IOImpl();
        Logic logic = new Logic(questions, io);
        while (true) {
            logic.ShowQuestion();
        }
    }
}