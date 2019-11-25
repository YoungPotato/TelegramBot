package main_package.questions;

import java.util.List;

public class QnA {
    private String question;
    private List<String> answers;
    private String correctAnswer;

    public QnA(String question, List<String> answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        String immutableQuestion = question;
        return immutableQuestion;
    }

    public List<String> getAnswers() {
        List<String> immutableAnswers = answers;
        return immutableAnswers;
    }

    public String getCorrectAnswer() {
        String immutableCorrectAnswer = correctAnswer;
        return immutableCorrectAnswer;
    }
}
