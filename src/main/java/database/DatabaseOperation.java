package database;

import questions.QnA;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseOperation implements IDatabaseOperation {
    private Database database;

    public DatabaseOperation() throws SQLException, ClassNotFoundException {
        database = Database.getInstance();
    }
    @Override
    public void addCountResponses(String answer, String question) throws SQLException {
        List<String> columns = Arrays.asList("question", "count_answer1", "answer1", "count_answer2", "answer2",
                "count_answer3", "answer3", "count_answer4", "answer4");
        List<List<String>> data = getData(columns);
        System.out.println(data);
        List<String> row = new ArrayList<>();
        for (List<String> a: data) {
            System.out.println(a);
            if (a.get(0).equals(question)) {
                row = a;
                break;
            }
        }
        System.out.println(row);
        int count = 0;
        String column = "";
        for (int i = 0; i < row.size(); i++) {
            if (row.get(i).equals(answer)) {
                column = columns.get(i - 1);
                System.out.println(column);
                count = Integer.parseInt(row.get(i-1));
                count +=1;
                System.out.println(count);
                break;
            }
        }
        database.update(column, Integer.toString(count), row.get(0));

    }

    @Override
    public void addQuestion(QnA qnA) throws SQLException {
        List<String> all_question = database.getAllQuestion();
        if (!all_question.contains(qnA.getQuestion())) {
            List<String> answers = new ArrayList<>();
            answers.add(qnA.getCorrectAnswer());
            for (String answer : qnA.getAnswers()) {
                if (!answer.equals(qnA.getCorrectAnswer()))
                    answers.add(answer);
            }
            List<String> data = Arrays.asList(qnA.getQuestion(), "0", answers.get(0), "0", answers.get(1), "0",
                    answers.get(2), "0", answers.get(3));
            database.write(data);
            System.out.println(data);
        }
    }

    @Override
    public List<List<String>> getData(List<String> columns) throws SQLException {
        return database.getData(columns);
    }
}
