package main_package;

import java.util.Scanner;

public class IO {
    public void printStart() {
        System.out.println("Привет, я консольный бот. Я умею задавать вопросы.");
    }
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void write(Question question) {
        System.out.println(question.question);
        for (int i = 0; i < 4; i++) {
            System.out.println(String.format("%s. %s", i + 1, question.answers.get(i)));
        }
    }

    public void printHelp() {
        System.out.println("Надо написать сюда что-то нормальное");
    }

    public void correctAnswer() {
        System.out.println("Це верно(Исправить)");
    }

    public void incorrectAnswer() {
        System.out.println("Це не верно. Тру эгейн(Исправить)");
    }
}
