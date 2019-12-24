package database;

import java.sql.SQLException;
import java.util.List;

public interface IDatabase {
    void write(List<String> data) throws SQLException;

    void update(String column, String count, String question) throws SQLException;

    List<List<String>> getData(List<String> columns) throws SQLException;
}
