package models.sqlTables;

import lombok.Data;
import utils.enums.ColumnLabels;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
public class AuthorTable {
    private Integer id;
    private String name;
    private String login;
    private String email;

    public AuthorTable() {}

    public AuthorTable(ResultSet resultSet) {
        try {
            id = resultSet.getInt(ColumnLabels.ID.getValue());
            name = resultSet.getString(ColumnLabels.NAME.getValue());
            login = resultSet.getString(ColumnLabels.LOGIN.getValue());
            email = resultSet.getString(ColumnLabels.EMAIL.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}