package tylerkv.rest.service;

import tylerkv.rest.db.DatabaseConnector;
import tylerkv.rest.db.DatabaseUtils;
import tylerkv.rest.model.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

public class OrderService {
    public static boolean AddOrder(Order order){
        Connection connection = null; 
        Statement stmt = null;
        try{
            connection = DatabaseConnector.getConnection();
            stmt = connection.createStatement();
            
            //construct person address query
            String person_address_query = "INSERT INTO address (address_1, address_2, city, state, zip) " + 
                "VALUES ('"+ order.getShipAddress() + "','" +
                    order.getShipAddress2() +"','" +
                    order.getCity() + "','" +
                    order.getState() + "','" +
                    order.getZip() + "')";
            
            //insert person address
            long person_address_id = -1;
            stmt.executeUpdate(person_address_query, Statement.RETURN_GENERATED_KEYS);   
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                person_address_id = rs.getLong(1);
            }
            
            //insert person
            stmt.executeUpdate("INSERT INTO person (address_id, email, first_name, last_name, phone) " +
                "VALUES ('" +
                    person_address_id + "','" + 
                    order.getEmail() + "','" + 
                    order.getFname() + "','" +
                    order.getLname() + "','" +
                    order.getPhone() + "')", Statement.RETURN_GENERATED_KEYS);
            long person_id = -1;
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                person_id = rs.getLong(1);
            }
            
            //insert pay address
            stmt.executeUpdate("INSERT INTO address (address_1, address_2, city, state, zip) " +
                "VALUES ('" +
                    order.getBillAddress() + "','" +
                    order.getBillAddress2() + "','" +
                    order.getCity() + "','" +
                    order.getBillState() + "','" +
                    order.getBillZip() + "')", Statement.RETURN_GENERATED_KEYS);
            long pay_address_id = -1;
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                pay_address_id = rs.getLong(1);
            }            
            
            //insert payment
            stmt.executeUpdate("INSERT INTO payment (address_id,card_number,csc,expiration,name) " +
                "VALUES ('" +
                pay_address_id + "','" +
                order.getCardNumber() + "','" +
                order.getCsc() + "','" +
                order.getExpiration() + "','" +
                order.getCardName() + "')", Statement.RETURN_GENERATED_KEYS);
            long payment_id = -1;
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                payment_id = rs.getLong(1);
            }              
           
            //insert order
            stmt.executeUpdate("INSERT INTO orders (payment_id,person_id,cart_string) " +
                "VALUES ('" +
                payment_id + "','" +
                person_id + "','" +
                order.getCartString() + "')", Statement.RETURN_GENERATED_KEYS);

            long order_id = -1;
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                order_id = rs.getLong(1);
            }   
            
            if (order_id != -1){
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(stmt != null){
                try{
                    stmt.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
