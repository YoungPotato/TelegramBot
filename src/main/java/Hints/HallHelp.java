package Hints;

import database.DatabaseOperation;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.sql.SQLException;
import java.util.*;

public class HallHelp implements IHint{
    public String getPopularAnswer(String question) throws SQLException, ClassNotFoundException {
        List<String> columns = Arrays.asList("question", "count_answer1", "answer1", "count_answer2", "answer2",
                "count_answer3", "answer3", "count_answer4", "answer4");
        DatabaseOperation databaseOperation = new DatabaseOperation();
        List<List<String>> data = databaseOperation.getData(columns);
        List<String> a = new ArrayList<>();
        for (List<String> row: data) {
            if (row.get(0).equals(question)) {
                a = row;
                break;
            }
        }
        int sum_count_answers = 0;
        TreeMap<String, Integer> statistics = new TreeMap<>();
        for (int i = 1; i < columns.size(); i+=2) {
            sum_count_answers += Integer.parseInt(a.get(i));
            statistics.put(a.get(i+1), Integer.parseInt(a.get(i)));
        }
        if (sum_count_answers == 0) {
            sum_count_answers = 1;
        }
        StringBuilder result = new StringBuilder();
        for (String key: statistics.keySet()) {
            result.append(String.format("\n%s - %d%%", key, 100/sum_count_answers * statistics.get(key)));
        }
        return result.toString();
    }

    @Override
    public ImmutablePair<String, String> getName() {
        return new ImmutablePair<>("/hallHelp", "hall");
    }
}
