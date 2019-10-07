package main_package;

import java.io.IOException;

public class Logic {
    public void start() throws IOException {
        Questions questions = new Questions();
        Question question = new Question();
        boolean newQuestion = true;
        IO io = new IO();
        while (true) {
            if (newQuestion) {
                question = questions.getQuestion(1);
                io.write(question);
            }
            String input = io.read();
            if (input.equals("\\help")) {
                io.printHelp();
                newQuestion = false;
            }
            else if (input.equals("\\stop"))
              System.exit(0);
            else if (input.equals(question.correctAnswer)) {
              io.correctAnswer();
              newQuestion = true;
            }
            else {
              io.incorrectAnswer();
              newQuestion = false;
            }
        }
    }
}
