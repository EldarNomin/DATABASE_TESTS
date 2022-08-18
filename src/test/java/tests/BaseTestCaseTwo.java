package tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import db.DatabaseConnection;
import db.TestDB;
import models.sqlTables.TestTable;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.RandomUtil;
import utils.enums.ColumnLabels;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaseTestCaseTwo {
    protected final Logger logger = AqualityServices.getLogger();
    protected static List<TestTable> testTables = new ArrayList<>();
    protected List<TestTable> listForUpdate;
    protected int insertId;
    protected List<Integer> insertedListId = new ArrayList<>();
    protected List<Integer> idFromNeededTests =  new ArrayList<>();
    private static final TestDB TEST_DB = new TestDB();
    private static final ISettingsFile PROJ_SETTINGS = new JsonSettingsFile("projSettings.json");

    @BeforeMethod
    public void beforeMethod() {
        logger.info("A selection of no more than 10 tests from the database where id contains two random repeating digits");
        List<TestTable> testTableList = TEST_DB.getAll();
        int digitCapacity = 2;
        int selectorId = RandomUtil.getRandomRepeatingNumber(digitCapacity);
        int listSize = 10;

        for (TestTable testTable : testTableList) {
            if (testTable.getId().toString().contains(String.valueOf(selectorId))) {
                testTables.add(testTable);
            }
            if (testTables.size() >= listSize) {
                break;
            }
        }
        logger.info("Selection complete");

        logger.info("Copy tests with current project and author");
        for (TestTable testTable : testTables) {
            testTable.setProjectId((Integer) PROJ_SETTINGS.getValue(ColumnLabels.PROJECT_ID.getValueJson()));
            testTable.setAuthorId((Integer) PROJ_SETTINGS.getValue(ColumnLabels.AUTHOR_ID.getValueJson()));
        }
        logger.info("Copying of tests indicating the current project and author is completed");
    }

    @AfterMethod
    public void afterMethod() {
        logger.info("Removing created entry from the database");
        for (TestTable testTable : listForUpdate) {
            TEST_DB.delete(testTable);
        }
        logger.info("Removing created entry from the database completed");
    }

    @AfterSuite
    public void tearDown() {
        DatabaseConnection.closeConnection();
    }
}