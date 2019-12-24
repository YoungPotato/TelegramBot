package Hints;

import database.DatabaseOperation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import questions.QnA;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Delete2IncorrectAnswers implements IHint {
    public QnA delete2IncorrectAnswers(QnA qnA) throws SQLException, ClassNotFoundException {
        List<String> columns = Arrays.asList("question", "count_answer1", "answer1", "count_answer2", "answer2",
                "count_answer3", "answer3", "count_answer4", "answer4");
        DatabaseOperation databaseOperation = new DatabaseOperation();
        List<List<String>> data = databaseOperation.getData(columns);
        List<String> a = new ArrayList<>();
        for (List<String> row: data) {
            if (row.get(0).equals(qnA.getQuestion())) {
                a = row;
                break;
            }
        }
        List<Integer> count = new ArrayList<>();
        for (String s : a) {
            try {
                count.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {
            }
        }
        count.sort(Collections.reverseOrder());
        String mostPopular = count.get(0).toString();
        String answer = "";
        for (int i =0; i < a.size(); i++) {
            if (a.get(i).equals(mostPopular)) {
                answer = a.get(i + 1);
                if (!qnA.getCorrectAnswer().equals(answer)) {
                    break;
                }
            }
        }
        List<String> answers = Arrays.asList(qnA.getCorrectAnswer(), answer);
        Collections.shuffle(answers);
        return new QnA(qnA.getQuestion(), answers, qnA.getCorrectAnswer());
    }

    @Override
    public ImmutablePair<String, String> getName() {
        return new ImmutablePair<>("/delete2IncorrectAnswers", "50/50");
    }
}
