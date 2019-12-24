package Hints;

import database.DatabaseOperation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import questions.QnA;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReplaceQuestion implements IHint {
    public QnA replaceQuestion() throws SQLException, ClassNotFoundException {
        List<String> columns = Arrays.asList("question", "count_answer1", "answer1", "count_answer2", "answer2",
                "count_answer3", "answer3", "count_answer4", "answer4");
        DatabaseOperation databaseOperation = new DatabaseOperation();
        List<List<String>> data = databaseOperation.getData(columns);
        List<String> a = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (List<String> row: data) {
            if (Integer.parseInt(row.get(1)) < min) {
                min = Integer.parseInt(row.get(1));
                a = row;
            }
        }
        List<String> replaceAnswers = Arrays.asList(a.get(2), a.get(4), a.get(6), a.get(8));
        return new QnA(a.get(0), replaceAnswers, a.get(2));
    }

    @Override
    public ImmutablePair<String, String> getName() {
        return new ImmutablePair<>("/replaceQuestion", "?");
    }
}
