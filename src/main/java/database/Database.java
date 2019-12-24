package database;

import javax.swing.event.ListDataEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Database implements IDatabase {
    private static Database instance;
    private Statement statement;

    private Database() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:Question.db");
        statement = connection.createStatement();
        statement.execute("DROP TABLE questions_statistics");
        statement.execute("CREATE TABLE if not exists 'questions_statistics' ('question' text, 'count_answer1' text, 'answer1' text, " +
                        "'count_answer2' text, 'answer2' text, 'count_answer3' text, 'answer3' text, 'count_answer4' text, " +
                        "'answer4' text);");

    }

    public static Database getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    @Override
    public void write(List<String> data) throws SQLException {
        statement.execute(String.format("INSERT INTO 'questions_statistics' ('question', 'count_answer1', 'answer1', " +
                        "'count_answer2', 'answer2', 'count_answer3', 'answer3', 'count_answer4', 'answer4') " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'); ",
                data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5), data.get(6), data.get(7), data.get(8)));
    }

    @Override
    public void update(String column, String count, String question) throws SQLException {
        System.out.println(String.format("UPDATE 'questions_statistics' SET %s = '%s' WHERE question = '%s'",
                column, count, question));
        statement.execute(String.format("UPDATE 'questions_statistics' SET %s = '%s' WHERE question = '%s'",
                column, count, question));
    }

    @Override
    public List<List<String>> getData(List<String> columns) throws SQLException {
        List<List<String>> data = new ArrayList<>();
        ResultSet result = statement.executeQuery("SELECT * FROM questions_statistics");
        while(result.next()) {
            List<String> arguments = new ArrayList<>();
            for (String column : columns) arguments.add(result.getString(column));
            data.add(arguments);
        }
        return data;
    }

    public List<String> getAllQuestion() throws SQLException {
        List<String> result = new ArrayList<>();
        ResultSet res = statement.executeQuery("SELECT * FROM questions_statistics");
        while(res.next()) {
            result.add(res.getString("question"));
        }
        return result;
    }
}