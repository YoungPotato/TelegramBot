package main_package;

import main_package.io.IOImpl;
import main_package.questions.Question;
import main_package.questions.Questions;
import main_package.io.Messages;

import java.io.IOException;

public class Logic  {
    private Questions questions;
    private IOImpl io;
    private Question question;

    public Logic(Questions questions, IOImpl io) {
        this.questions = questions;
        this.io = io;
        question = new Question();
    }

    public void ShowQuestion() throws IOException {
        question = questions.getQuestion(3);
        printQuestion(question);
        String input = io.read();
        if (input.equals("/help")) {
            io.write(Messages.HELP);
        } else if (isCorrectAnswer(input)) {
            io.write(Messages.CORRECT);
        } else {
            io.write(Messages.INCORRECT);
        }
    }

    private void printQuestion(Question question) {
        io.write(question.question);
        for (int i = 0; i < question.answers.size(); i++) {
            io.write(String.format("%s. %s", i + 1, question.answers.get(i)));
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
