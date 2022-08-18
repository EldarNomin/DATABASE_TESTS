package db;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import dao.IDao;
import models.sqlTables.AuthorTable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuthorDB implements IDao<AuthorTable> {

    private final Logger log = AqualityServices.getLogger();
    private static final String SQL_GET_ATTRIBUTES = "SELECT * FROM AUTHOR";
    private static final String SQL_INSERT = "INSERT INTO AUTHOR (name, login, email) values (?, ?, ?)";

    @Override
    public List<AuthorTable> getAll() {
        Connection connection = DatabaseConnection.getConnection();
        List<AuthorTable> authorTables = new ArrayList<>();
        ResultSet resultSet;

        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ATTRIBUTES)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                authorTables.add(new AuthorTable(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return authorTables;
    }

    @Override
    public List<AuthorTable> get(String request) {
        return Collections.emptyList();
    }

    @Override
    public int insert(AuthorTable authorTable) {
        int id = 0;
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, authorTable.getName());
            statement.setString(2, authorTable.getLogin());
            statement.setString(3, authorTable.getEmail());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    authorTable.setId((int) generatedKeys.getLong(1));

                    id = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("No ID obtained.");
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return id;
    }

    @Override
    public void update(AuthorTable authorTable) {
        update(authorTable);
    }

    @Override
    public void delete(AuthorTable authorTable) {
        delete(authorTable);
    }
}