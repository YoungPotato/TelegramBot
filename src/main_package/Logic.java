package main_package;

import main_package.io.IO;
import main_package.questions.Question;
import main_package.questions.Questions;

public class Logic  {
    private Questions questions;
    private IO io;
    private String apiUrl;
    private Boolean newQuestion;
    private Question question;

    public Logic(Questions questions, IO io, String apiUrl) {
        this.questions = questions;
        this.io = io;
        this.apiUrl = apiUrl;
        newQuestion = true;
        question = new Question();
    }

    public void ShowQuestion() {
        try {
            if (newQuestion) {
                question = questions.getQuestion(apiUrl, 3);
                io.write(question);
            }
            String input = io.read();
            if (input.equals("\\help")) {
                io.printHelp();
                newQuestion = false;
            } else if (input.equals("\\stop"))
                System.exit(0);
            else if (input.equals(question.correctAnswer) || question.answers.get(Integer.parseInt(input) - 1).equals(question.correctAnswer)) {
                io.correctAnswer();
                newQuestion = true;
            } else {
                io.incorrectAnswer();
                newQuestion = false;
            }
        }
        catch (Exception e) {
            io.incorrectAnswer();
            newQuestion = false;
        }
    }
}
