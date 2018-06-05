package tylerkv.rest.service;

import tylerkv.rest.db.DatabaseConnector;
import tylerkv.rest.db.DatabaseUtils;
import tylerkv.rest.model.Rock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RockService {
    
    private final static String ALL_ROCKS_QUERY = "SELECT * FROM rocks";
    
    public static Rock getRockById(int id) {
        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_ROCKS_QUERY + " WHERE rock_id = " + id);
        
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Rock rock = new Rock();
                    
                    rock.setRockId(resultSet.getInt("rock_id"));
                    rock.setColor(resultSet.getString("color"));
                    rock.setQuantityPerOrder(resultSet.getInt("quantity_per_order"));
                    rock.setPricePerOrder(resultSet.getFloat("price_per_order"));
                    rock.setDescription(resultSet.getString("description"));
                    
                    return rock;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e ) {
                    e.printStackTrace();
                }
            }
        }
        
        return null;
    }
    
    public static List<Rock> getAllRocks() {
        List<Rock> rocks = new ArrayList<Rock>();
        
        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_ROCKS_QUERY);
        
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Rock rock = new Rock();
                    
                    rock.setRockId(resultSet.getInt("rock_id"));
                    rock.setColor(resultSet.getString("color"));
                    rock.setQuantityPerOrder(resultSet.getInt("quantity_per_order"));
                    rock.setPricePerOrder(resultSet.getFloat("price_per_order"));
                    rock.setDescription(resultSet.getString("description"));
                    
                    rocks.add(rock);
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
        
        return rocks;
    }
    
    public static boolean AddRock(Rock rock) {
        String sql = "INSERT INTO rocks (color, quantity_per_order, price_per_order, description) " +
                "VALUES (?, ?, ?, ?)";
        Connection connection = DatabaseConnector.getConnection();
        return DatabaseUtils.performDBUpdate(connection, sql, rock.getColor(), 
                String.valueOf(rock.getQuantityPerOrder()),
                String.valueOf(rock.getPricePerOrder()),
                rock.getDescription());
    }
    
    public static boolean updateRock(Rock rock) {
        String sql = "UPDATE rocks SET color=?, quantity_per_order=?, price_per_order=?, description=? " +
                "WHERE rock_id=?;";
        
        Connection connection = DatabaseConnector.getConnection();
        
        boolean updateStatus = DatabaseUtils.performDBUpdate(connection, sql, rock.getColor(), 
                String.valueOf(rock.getQuantityPerOrder()),
                String.valueOf(rock.getPricePerOrder()),
                rock.getDescription(),
                String.valueOf(rock.getRockId()));
        
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
                
        return updateStatus;
    }
    
    public static boolean deleteRock(Rock retrievedRock) {
        String sql = "DELETE FROM rocks WHERE rock_id=?;";
        
        Connection connection = DatabaseConnector.getConnection();
        
        boolean updateStatus = DatabaseUtils.performDBUpdate(connection, sql, String.valueOf(retrievedRock.getRockId()));
        
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return updateStatus;
    }
}
