package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import db.AuthorDB;
import db.ProjectDB;
import db.SessionDB;
import db.StatusDB;
import models.sqlTables.*;
import utils.enums.ColumnLabels;
import java.sql.Timestamp;
import java.util.List;

public class SQLUtils {
    private static final ISettingsFile DEFAULT_STATUS_VALUES =
            new JsonSettingsFile("defaultStatusValues.json");
    private static final ISettingsFile DEFAULT_PROJECT_VALUES =
            new JsonSettingsFile("defaultProjectValues.json");
    private static final ISettingsFile DEFAULT_SESSION_VALUES =
            new JsonSettingsFile("defaultSessionValues.json");
    private static final ISettingsFile DEFAULT_AUTHOR_VALUES =
            new JsonSettingsFile("defaultAuthorValues.json");

    public static void defaultForeignKeyValuesTestTable(List<TestTable> testTables) {
        StatusDB statusDB = new StatusDB();
        List<StatusTable> statusTables = statusDB.getAll();

        ProjectDB projectDB = new ProjectDB();
        List<ProjectTable> projectTables = projectDB.getAll();

        SessionDB sessionDB = new SessionDB();
        List<SessionTable> sessionTables = sessionDB.getAll();

        AuthorDB authorDB = new AuthorDB();
        List<AuthorTable> authorTables = authorDB.getAll();

        for (TestTable testTable : testTables) {
            if (testTable.getStatusId() == 0) {
                if (statusTables.size() == 0) {
                    setDefaultStatusValues(statusDB);
                }
                testTable.setStatusId((int) DEFAULT_STATUS_VALUES.getValue(ColumnLabels.ID.getValueJson()));
            } else if (testTable.getProjectId() == 0) {
                if (projectTables.size() == 0) {
                    setDefaultProjectValues(projectDB);
                }
                testTable.setProjectId((int) DEFAULT_PROJECT_VALUES.getValue(ColumnLabels.ID.getValueJson()));
            } else if (testTable.getSessionId() == 0) {
                if (sessionTables.size() == 0) {
                    setDefaultSessionValues(sessionDB);
                }
                testTable.setSessionId((int) DEFAULT_SESSION_VALUES.getValue(ColumnLabels.ID.getValueJson()));
            } else if (testTable.getAuthorId() == 0) {
                if (authorTables.size() == 0) {
                    setDefaultValuesAuthorTable(authorDB);
                }
                testTable.setAuthorId((int) DEFAULT_AUTHOR_VALUES.getValue(ColumnLabels.ID.getValueJson()));
            }
        }
    }

    private static void setDefaultStatusValues(StatusDB statusDB) {
        StatusTable statusTable = new StatusTable();
        statusTable.setId((int) DEFAULT_STATUS_VALUES.getValue(ColumnLabels.ID.getValueJson()));
        statusTable.setName(DEFAULT_STATUS_VALUES.getValue(ColumnLabels.NAME.getValueJson()).toString());
        statusDB.insert(statusTable);
    }

    private static void setDefaultProjectValues(ProjectDB projectDB) {
        ProjectTable projectTable = new ProjectTable();
        projectTable.setId((int) DEFAULT_PROJECT_VALUES.getValue(ColumnLabels.ID.getValueJson()));
        projectTable.setName(DEFAULT_PROJECT_VALUES.getValue(ColumnLabels.NAME.getValueJson()).toString());
        projectDB.insert(projectTable);
    }

    private static void setDefaultSessionValues(SessionDB sessionDB) {
        SessionTable sessionTable = new SessionTable();
        sessionTable.setId((int) DEFAULT_SESSION_VALUES.getValue(ColumnLabels.ID.getValueJson()));
        sessionTable.setSessionKey(DEFAULT_SESSION_VALUES.getValue(ColumnLabels.SESSION_KEY.getValueJson()).toString());
        sessionTable.setCreatedTime((Timestamp) DEFAULT_SESSION_VALUES.getValue(ColumnLabels.CREATED_TIME.getValueJson()));
        sessionTable.setBuildNumber((int) DEFAULT_SESSION_VALUES.getValue(ColumnLabels.BUILD_NUMBER.getValueJson()));
        sessionDB.insert(sessionTable);
    }

    private static void setDefaultValuesAuthorTable(AuthorDB authorDB) {
        AuthorTable authorTable = new AuthorTable();
        authorTable.setId((int) DEFAULT_AUTHOR_VALUES.getValue(ColumnLabels.ID.getValueJson()));
        authorTable.setName(DEFAULT_AUTHOR_VALUES.getValue(ColumnLabels.NAME.getValueJson()).toString());
        authorTable.setLogin(DEFAULT_AUTHOR_VALUES.getValue(ColumnLabels.LOGIN.getValueJson()).toString());
        authorTable.setEmail(DEFAULT_AUTHOR_VALUES.getValue(ColumnLabels.EMAIL.getValueJson()).toString());
        authorDB.insert(authorTable);
    }
}