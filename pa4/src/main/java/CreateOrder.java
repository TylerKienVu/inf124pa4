import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.*;  

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/order")
public class CreateOrder extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException{
        doPost(req,res);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        PrintWriter out = response.getWriter();
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://matt-smith-v4.ics.uci.edu/inf124db061","inf124db061","TMcVwhIMAmW^");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf124?autoReconnect=true&useSSL=false","root","mTigerl8855!");
            stmt = conn.createStatement();
            
            //grab cart
            HttpSession session = request.getSession(true);
            String cartKey = new String("cart");
            List<String> cartList = null;
            cartList = (List<String>) session.getAttribute(cartKey);
            
            if(cartList == null){
                out.println("returned null");
                return;
            }
            
            String cart_string = "";
            for(int i = 0; i < cartList.size(); i++){
                String rock_id = cartList.get(i);
                cart_string += rock_id;
            }
            
            out.println("starting queries");
            
            //construct person address query
            String person_address_query = "INSERT INTO address (address_1, address_2, city, state, zip) " + 
                "VALUES ('"+ request.getParameter("shipAddress") + "','" +
                    request.getParameter("shipAddress2") +"','" +
                    request.getParameter("city") + "','" +
                    request.getParameter("state") + "','" +
                    request.getParameter("zip") + "')";
            
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
                    request.getParameter("email") + "','" + 
                    request.getParameter("fname") + "','" +
                    request.getParameter("lname") + "','" +
                    request.getParameter("phone") + "')", Statement.RETURN_GENERATED_KEYS);
            long person_id = -1;
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                person_id = rs.getLong(1);
            }
            
            //insert pay address
            stmt.executeUpdate("INSERT INTO address (address_1, address_2, city, state, zip) " +
                "VALUES ('" +
                    request.getParameter("billAddress") + "','" +
                    request.getParameter("billAddress2") + "','" +
                    request.getParameter("billCity") + "','" +
                    request.getParameter("billState") + "','" +
                    request.getParameter("billZip") + "')", Statement.RETURN_GENERATED_KEYS);
            long pay_address_id = -1;
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                pay_address_id = rs.getLong(1);
            }            
            
            //insert payment
            stmt.executeUpdate("INSERT INTO payment (address_id,card_number,csc,expiration,name) " +
                "VALUES ('" +
                pay_address_id + "','" +
                request.getParameter("cardNumber") + "','" +
                request.getParameter("csc") + "','" +
                request.getParameter("expiration") + "','" +
                request.getParameter("cardName") + "')", Statement.RETURN_GENERATED_KEYS);
            long payment_id = -1;
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                payment_id = rs.getLong(1);
            }              
           
            out.println("before order query");
            out.println("payment_id: " + payment_id);
            out.println("person_id: " + person_id);
            out.println("cart_string: " + cart_string);
            //insert order
            stmt.executeUpdate("INSERT INTO orders (payment_id,person_id,cart_string) " +
                "VALUES ('" +
                payment_id + "','" +
                person_id + "','" +
                cart_string + "')", Statement.RETURN_GENERATED_KEYS);
            out.println("after query");
            long order_id = -1;
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                order_id = rs.getLong(1);
            } 

            out.println("person_address_id: " + person_address_id);
            out.println("pay_address_id:" + pay_address_id);
            out.println("payment_id: " + payment_id);
            out.println("person_id: " + person_id);
            out.println("order_id: " + order_id);
            
            //forward to orderDetails
            RequestDispatcher rd = request.getRequestDispatcher("html/receipt.jsp");
            rd.forward(request,response);
            
            out.flush();
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
            if(conn != null){
                try{
                    conn.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
