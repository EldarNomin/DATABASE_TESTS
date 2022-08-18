package models.sqlTables;

import lombok.Data;
import utils.enums.ColumnLabels;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Data
public class TestTable {
    private Integer id;
    private String name;
    private Integer statusId;
    private String methodName;
    private Integer projectId;
    private Integer sessionId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String env;
    private String browser;
    private Integer authorId;

    public TestTable() {

    }

    public TestTable(ResultSet resultSet) {
        try {
            id = resultSet.getInt(ColumnLabels.ID.getValue());
            name = resultSet.getString(ColumnLabels.NAME.getValue());
            statusId = resultSet.getInt(ColumnLabels.STATUS_ID.getValue());
            methodName = resultSet.getString(ColumnLabels.METHOD_NAME.getValue());
            projectId = resultSet.getInt(ColumnLabels.PROJECT_ID.getValue());
            sessionId = resultSet.getInt(ColumnLabels.SESSION_ID.getValue());
            startTime = resultSet.getTimestamp(ColumnLabels.START_TIME.getValue());
            endTime = resultSet.getTimestamp(ColumnLabels.END_TIME.getValue());
            env = resultSet.getString(ColumnLabels.ENV.getValue());
            browser = resultSet.getString(ColumnLabels.BROWSER.getValue());
            authorId = resultSet.getInt(ColumnLabels.AUTHOR_ID.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}