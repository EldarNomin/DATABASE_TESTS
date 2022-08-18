package db;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import dao.IDao;
import models.sqlTables.TestTable;
import utils.SQLUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDB implements IDao<TestTable> {

    private static final Logger log = AqualityServices.getLogger();
    private static final String SQL_GET_ATTRIBUTES = "SELECT * FROM TEST";
    private static final String SQL_INSERT = "INSERT INTO TEST (name, status_id, method_name, project_id, " +
            "session_id, start_time, end_time, env, browser, author_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE TEST SET " +
            "name = ?, " +
            "status_id = ?, " +
            "method_name = ?, " +
            "project_id = ?, " +
            "session_id = ?, " +
            "start_time = ?, " +
            "end_time = ?, " +
            "env = ?, " +
            "browser = ?, " +
            "author_id = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM TEST WHERE id = ?";

    @Override
    public List<TestTable> getAll() {
        Connection connection = DatabaseConnection.getConnection();
        List<TestTable> testTables = new ArrayList<>();
        ResultSet resultSet;

        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ATTRIBUTES)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                testTables.add(new TestTable(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        SQLUtils.defaultForeignKeyValuesTestTable(testTables);

        return testTables;
    }

    @Override
    public List<TestTable> get(String request) {
        Connection connection = DatabaseConnection.getConnection();
        List<TestTable> testTables = new ArrayList<>();
        ResultSet resultSet;

        try (PreparedStatement statement = connection.prepareStatement(request)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                testTables.add(new TestTable(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return testTables;
    }

    @Override
    public int insert(TestTable testTable) {
        Connection connection = DatabaseConnection.getConnection();
        List<TestTable> testTables = new ArrayList<>();
        int id = 0;

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, testTable.getName());
            statement.setInt(2, testTable.getStatusId());
            statement.setString(3, testTable.getMethodName());
            statement.setInt(4, testTable.getProjectId());
            statement.setInt(5, testTable.getSessionId());
            statement.setTimestamp(6, testTable.getStartTime());
            statement.setTimestamp(7, testTable.getEndTime());
            statement.setString(8, testTable.getEnv());
            statement.setString(9, testTable.getBrowser());
            statement.setInt(10, testTable.getAuthorId());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        testTables.add(testTable);
        return id;
    }

    @Override
    public void update(TestTable testTable) {
        Connection connection = DatabaseConnection.getConnection();
        List<TestTable> testTables = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, testTable.getName());
            statement.setInt(2, testTable.getStatusId());
            statement.setString(3, testTable.getMethodName());
            statement.setInt(4, testTable.getProjectId());
            statement.setInt(5, testTable.getSessionId());
            statement.setTimestamp(6, testTable.getStartTime());
            statement.setTimestamp(7, testTable.getEndTime());
            statement.setString(8, testTable.getEnv());
            statement.setString(9, testTable.getBrowser());
            statement.setInt(10, testTable.getAuthorId());
            statement.setInt(11, testTable.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        testTables.add(testTable);
    }

    @Override
    public void delete(TestTable testTable) {
        Connection connection = DatabaseConnection.getConnection();
        List<TestTable> testTables = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, testTable.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        testTables.remove(testTable);
    }
}