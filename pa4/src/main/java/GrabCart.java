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

@WebServlet("/grabcart")
public class GrabCart extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException{
        doGet(req,res);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://matt-smith-v4.ics.uci.edu/inf124db061","inf124db061","TMcVwhIMAmW^");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf124?autoReconnect=true&useSSL=false","root","mTigerl8855!");
            stmt = conn.createStatement();
            HttpSession session = request.getSession(true);
            String cartKey = new String("cart");
            List<String> cartList = null;
            
            
            cartList = (List<String>) session.getAttribute(cartKey);
            if(cartList == null){
                cartList = new ArrayList<String>();
                session.setAttribute(cartKey,cartList);
            }
            
            for(int i = 0; i < cartList.size(); i++){
                String rock_id = cartList.get(i);
                rs = stmt.executeQuery("SELECT * FROM rocks WHERE rock_id = " + rock_id);
                if(rs.next()){
                    out.println(rock_id + " " + rs.getString("price_per_order"));                    
                }
            }
            
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
