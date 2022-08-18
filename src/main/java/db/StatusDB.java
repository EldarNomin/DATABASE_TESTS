package db;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import dao.IDao;
import models.sqlTables.StatusTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatusDB implements IDao<StatusTable> {

    private final Logger log = AqualityServices.getLogger();
    private static final String SQL_GET_ATTRIBUTES = "SELECT * FROM STATUS";
    private static final String SQL_INSERT = "INSERT INTO STATUS (name) values (?)";

    @Override
    public List<StatusTable> getAll() {
        Connection connection = DatabaseConnection.getConnection();
        List<StatusTable> statusTables = new ArrayList<>();
        ResultSet resultSet;

        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ATTRIBUTES)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                statusTables.add(new StatusTable(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return statusTables;
    }

    @Override
    public List<StatusTable> get(String request) {
        return null;
    }

    @Override
    public int insert(StatusTable statusTable) {
        int id = 0;
        Connection connection = DatabaseConnection.getConnection();
        List<StatusTable> statusTables = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, statusTable.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        statusTables.add(statusTable);
        return id;
    }

    @Override
    public void update(StatusTable statusTable) {
    }

    @Override
    public void delete(StatusTable statusTable) {
    }
}