package main_package;

import main_package.io.IOImpl;
import main_package.questions.QnA;
import main_package.io.Message;
import main_package.questions.QuestionsProvider;

import java.io.IOException;

public class Logic  {
    private QuestionsProvider questions;
    private IOImpl io;
    private QnA question;
    private Boolean newQuestion;

    public Logic(QuestionsProvider questions, IOImpl io) {
        this.questions = questions;
        this.io = io;
        newQuestion = true;
        io.write(Message.START);
    }

    public void ShowQuestion() throws IOException {
        if (newQuestion) {
            question = questions.getQuestion(3);
            io.write(question);
        }
        String input = io.read();
        if (input.equals("/help")) {
            io.write(Message.HELP);
            newQuestion = false;
        } else if (isCorrectAnswer(input)) {
            io.write(Message.CORRECT);
            newQuestion = true;
        } else {
            io.write(Message.INCORRECT);
            newQuestion = true;
        }
    }

    private Boolean isCorrectAnswer(String input) {
        try {
            return input.equals(question.getCorrectAnswer())
                    || question.getAnswers().get(Integer.parseInt(input) - 1).equals(question.getCorrectAnswer());
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
