package db;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import dao.IDao;
import models.sqlTables.SessionTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SessionDB implements IDao<SessionTable> {

    private final Logger log  = AqualityServices.getLogger();
    String sqlGetAttributes = "SELECT * FROM SESSION";
    String sqlInsert = "INSERT INTO SESSION (session_key, created_time, build_number) values (?, ?, ?)";

    @Override
    public List<SessionTable> getAll() {
        Connection connection = DatabaseConnection.getConnection();
        List<SessionTable> sessionTables = new ArrayList<>();
        ResultSet resultSet;

        try (PreparedStatement statement = connection.prepareStatement(sqlGetAttributes)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sessionTables.add(new SessionTable(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return sessionTables;
    }

    @Override
    public List<SessionTable> get(String request) {
        return null;
    }

    @Override
    public int insert(SessionTable sessionTable) {
        int id = 0;
        Connection connection = DatabaseConnection.getConnection();
        List<SessionTable> sessionTables = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sqlInsert)) {
            statement.setString(1, sessionTable.getSessionKey());
            statement.setTimestamp(2, sessionTable.getCreatedTime());
            statement.setInt(3, sessionTable.getBuildNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        sessionTables.add(sessionTable);
        return id;
    }

    @Override
    public void update(SessionTable sessionTable) {
        update(sessionTable);
    }

    @Override
    public void delete(SessionTable sessionTable) {
        delete(sessionTable);
    }
}