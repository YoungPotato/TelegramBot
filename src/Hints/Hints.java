package Hints;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;

public class Hints {
    private Delete2IncorrectAnswers delete2IncorrectAnswers;
    private ReplaceQuestion replaceQuestion;
    private RightMakeMistake rightMakeMistake;

    public Hints() {
        delete2IncorrectAnswers = new Delete2IncorrectAnswers();
        replaceQuestion = new ReplaceQuestion();
        rightMakeMistake = new RightMakeMistake();
    }

    public Boolean getDelete2IncorrectAnswers() {
        return delete2IncorrectAnswers.getValue();
    }
    public Boolean getReplaceQuestion() {
        return replaceQuestion.getValue();
    }
    public Boolean getRightMakeMistake() {
        return rightMakeMistake.getValue();
    }
    public Boolean getUsedRightMakeMistake() {
        return rightMakeMistake.getUsed();
    }

    public void setDelete2IncorrectAnswers() {
        delete2IncorrectAnswers.setValue();
    }
    public  void setReplaceQuestion() {
        replaceQuestion.setValue();
    }
    public void setRightMakeMistake() {
        rightMakeMistake.setValue();
    }
    public void setUsedRightMakeMistake() {
        rightMakeMistake.setUsed();
    }

    public List<ImmutablePair<String, String>> getAvailableHints() {
        List<ImmutablePair<String, String>> availableHints = new ArrayList<>();
        if (delete2IncorrectAnswers.getValue()) {
            availableHints.add(delete2IncorrectAnswers.getName());
        }
        if (replaceQuestion.getValue()) {
            availableHints.add(replaceQuestion.getName());
        }
        if (!rightMakeMistake.getUsed()) {
            availableHints.add(rightMakeMistake.getName());
        }
        return availableHints;
    }

}
