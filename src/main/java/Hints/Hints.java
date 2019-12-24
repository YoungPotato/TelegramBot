package Hints;

import org.apache.commons.lang3.tuple.ImmutablePair;
import questions.QnA;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Hints {
    private Delete2IncorrectAnswers delete2IncorrectAnswers;
    private ReplaceQuestion replaceQuestion;
    private HallHelp hallHelp;

    private Boolean isAvailableHallHelp = true;
    private Boolean isAvailableDelete2IncorrectAnswer = true;
    private Boolean isAvailableReplaceQuestion = true;


    public Hints() {
        delete2IncorrectAnswers = new Delete2IncorrectAnswers();
        replaceQuestion = new ReplaceQuestion();
        hallHelp = new HallHelp();
    }

    public Boolean isAvailableHallHelp() {
        return isAvailableHallHelp;
    }
    public Boolean isAvailableDelete2IncorrectAnswer() {
        return isAvailableDelete2IncorrectAnswer;
    }
    public Boolean isAvailableReplaceQuestion() {
        return isAvailableReplaceQuestion;
    }

    public String getHallHelp(String question) throws SQLException, ClassNotFoundException {
        isAvailableHallHelp = false;
        return hallHelp.getPopularAnswer(question);
    }

    public QnA delete2IncorrectAnswers(QnA qnA) throws SQLException, ClassNotFoundException {
        isAvailableDelete2IncorrectAnswer = false;
        return delete2IncorrectAnswers.delete2IncorrectAnswers(qnA);
    }

    public QnA replaceQuestion() throws SQLException, ClassNotFoundException {
        isAvailableReplaceQuestion = false;
        return replaceQuestion.replaceQuestion();
    }


    public List<ImmutablePair<String, String>> getAvailableHints() {
        List<ImmutablePair<String, String>> availableHints = new ArrayList<>();
        if (isAvailableHallHelp) {
            availableHints.add(hallHelp.getName());
        }
        if (isAvailableDelete2IncorrectAnswer) {
            availableHints.add(delete2IncorrectAnswers.getName());
        }
        if (isAvailableReplaceQuestion) {
            availableHints.add(replaceQuestion.getName());
        }
        return availableHints;
    }
}
