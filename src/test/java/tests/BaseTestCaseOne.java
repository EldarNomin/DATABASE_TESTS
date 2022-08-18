package tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import db.DatabaseConnection;
import db.TestDB;
import models.sqlTables.TestTable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import utils.enums.ColumnLabels;
import java.sql.Timestamp;

public class BaseTestCaseOne {
    protected final Logger logger = AqualityServices.getLogger();

    @AfterMethod
    public void afterMethod(ITestResult result) {
        logger.info("Adding a new record to the database about the result of each test performed in the corresponding table");
        ISettingsFile projSettings = new JsonSettingsFile("projSettings.json");

        TestDB testDB = new TestDB();

        TestTable testTable = new TestTable();
        testTable.setName(result.getInstanceName());
        testTable.setStatusId(result.getStatus());
        testTable.setMethodName(result.getName());
        testTable.setProjectId((Integer) projSettings.getValue(ColumnLabels.PROJECT_ID.getValueJson()));
        testTable.setSessionId((Integer) projSettings.getValue(ColumnLabels.SESSION_ID.getValueJson()));
        testTable.setStartTime(new Timestamp(result.getStartMillis()));
        testTable.setEndTime(new Timestamp(result.getEndMillis()));
        testTable.setEnv(System.getenv(String.valueOf(projSettings.getValue(ColumnLabels.ENV.getValueJson()))));
        testTable.setBrowser(String.valueOf(projSettings.getValue(ColumnLabels.BROWSER.getValueJson())));
        testTable.setAuthorId((Integer) projSettings.getValue(ColumnLabels.AUTHOR_ID.getValueJson()));
        testDB.insert(testTable);
        logger.info("Entry added successfully");
    }

    @AfterSuite
    public void tearDown() {
        DatabaseConnection.closeConnection();
    }
}