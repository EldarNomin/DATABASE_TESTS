package models.sqlTables;

import lombok.Data;
import utils.enums.ColumnLabels;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class ProjectTable {
    private int id;
    private String name;

    public ProjectTable() {

    }

    public ProjectTable(ResultSet resultSet) {
        try {
            id = resultSet.getInt(ColumnLabels.ID.getValue());
            name = resultSet.getString(ColumnLabels.NAME.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
