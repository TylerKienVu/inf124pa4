package tylerkv.rest.main;

import tylerkv.rest.db.DatabaseConnector;
import tylerkv.rest.db.DatabaseUtils;
import java.sql.*;

public class test {
    public static void main(String[] args) {
        Connection connection = DatabaseConnector.getConnection();
        String query = "Select * from rocks";
        ResultSet results = DatabaseUtils.retrieveQueryResults(connection, query);
        
        if (results != null) {
            try {
                while (results.next()) {
                    System.out.println(results.getInt("rock_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
