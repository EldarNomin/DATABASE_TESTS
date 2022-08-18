package models.sqlTables;

import lombok.Data;
import utils.enums.ColumnLabels;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Data
public class SessionTable {
    private int id;
    private String sessionKey;
    private Timestamp createdTime;
    private int buildNumber;

    public SessionTable() {

    }

    public SessionTable(ResultSet resultSet) {
        try {
            id = resultSet.getInt(ColumnLabels.ID.getValue());
            sessionKey = resultSet.getString(ColumnLabels.SESSION_KEY.getValue());
            createdTime = resultSet.getTimestamp(ColumnLabels.CREATED_TIME.getValue());
            buildNumber = resultSet.getInt(ColumnLabels.BUILD_NUMBER.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
