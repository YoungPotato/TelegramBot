package main_package.io;
import main_package.questions.Question;

public interface IO {

    void printStart();

    String read();
    void write(Question question);

    void printHelp();

    void correctAnswer();
    void incorrectAnswer();
}
