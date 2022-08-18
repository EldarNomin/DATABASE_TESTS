package db;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import utils.enums.DBValue;
import java.sql.*;

public class DatabaseConnection {

    private static Logger log = AqualityServices.getLogger();
    private static final ISettingsFile DB_SETTINGS = new JsonSettingsFile("dbSettings.json");

    private static Connection connection = DatabaseConnection.getConnection();

    public DatabaseConnection(){}

    public static void setDatabaseConnection(){
        try {
            connection = DriverManager.getConnection(DB_SETTINGS.getValue(DBValue.URI.getValue()).toString(),
                    DB_SETTINGS.getValue(DBValue.USER.getValue()).toString(),
                    DB_SETTINGS.getValue(DBValue.PASSWORD.getValue()).toString());
            if (connection != null) {
                log.info("Connected to the database!");
            } else {
                log.info("Failed to make connection!");
            }
        } catch (SQLException e) {
            log.error(String.format("SQL state: %s\n%s", e.getSQLState(), e.getMessage()));
        }
}
    public static Connection getConnection() {
        if (connection == null) {
            setDatabaseConnection();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}