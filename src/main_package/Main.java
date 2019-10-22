package main_package;

import main_package.io.IO;
import main_package.io.IOImpl;
import main_package.questions.Questions;
import main_package.questions.QuestionsImpl;

public class Main {

    public static void main(String[] args) {
        IO io = new IOImpl();
        Questions questions = new QuestionsImpl();
        io.printStart();
        String apiUrl = "https://engine.lifeis.porn/api/millionaire.php?q=%s";
        Logic logic = new Logic(questions, io, apiUrl);
        while (true) {
            logic.ShowQuestion();
        }
    }
}
