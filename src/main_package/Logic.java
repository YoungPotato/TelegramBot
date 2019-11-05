package main_package;

import main_package.io.IOImpl;
import main_package.questions.Question;
import main_package.io.Message;
import main_package.questions.QuestionsProvider;

import java.io.IOException;

public class Logic  {
    private QuestionsProvider questions;
    private IOImpl io;
    private Question question;
    private Boolean newQuestion;

    public Logic(QuestionsProvider questions, IOImpl io) {
        this.questions = questions;
        this.io = io;
        question = new Question();
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
            return input.equals(question.correctAnswer)
                    || question.answers.get(Integer.parseInt(input) - 1).equals(question.correctAnswer);
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
