package database;

import questions.QnA;

import java.sql.SQLException;
import java.util.List;

public interface IDatabaseOperation {
    void addCountResponses(String column, String question) throws SQLException, ClassNotFoundException;

    void addQuestion(QnA qnA) throws SQLException;

    List<List<String>> getData(List<String> columns) throws SQLException;
}
