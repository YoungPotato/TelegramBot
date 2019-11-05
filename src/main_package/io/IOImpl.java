package main_package.io;

import main_package.questions.QnA;

import java.util.Scanner;

public class IOImpl implements IO {
    @Override
    public void write(Message message) {
        switch (message) {
            case HELP:
                System.out.println("Отвечайте на вопросы бота либо цифрами, либо словами");
                break;
            case START:
                System.out.println("Вы попали в игру \"Как стать миллионером\". Чат-бот будет выдавать вам вопросы, " +
                        "чтобы ответить на них, вводите цифру или текст с ответом в консоль. Удачи!");
                break;
            case CORRECT:
                System.out.println("Верно!");
                break;
            case INCORRECT:
                System.out.println("Неверно!");
                break;
        }
    }

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return (scanner.nextLine());
    }

    public void write(QnA question) {
        System.out.println(question.getQuestion());
        for (int i = 0; i < question.getAnswers().size(); i++) {
            System.out.println(String.format("%s. %s", i + 1, question.getAnswers().get(i)));
        }
    }
}