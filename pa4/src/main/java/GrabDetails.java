import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/details")
public class GrabDetails extends HttpServlet{
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
        String rock_id = request.getParameter("rock_id");
        PrintWriter out = response.getWriter();
        if(rock_id != null){ 
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://matt-smith-v4.ics.uci.edu/inf124db061","inf124db061","TMcVwhIMAmW^");
//                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inf124?autoReconnect=true&useSSL=false","root","mTigerl8855!");
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM rocks WHERE rock_id = " + rock_id);

                //session handling
                HttpSession session = request.getSession(true);
                String historyKey = new String("history");
                Queue<String> historyList = null;

                if (session.isNew()) {
                    historyList = new LinkedList<String>();
                    historyList.add(rock_id);
                    session.setAttribute(historyKey, historyList);
                }
                else {
                    historyList = (LinkedList<String>) session.getAttribute(historyKey);
                    if(historyList == null){
                        historyList = new LinkedList<String>();
                    }
                    if(historyList.size() == 5){
                        historyList.remove();
                    }
                    historyList.add(rock_id);
                    session.setAttribute(historyKey, historyList);
                }            

                //return details
                while(rs.next()){
                    out.println("<img class=\"product-detail-image\" src=\"../content/rock" + rock_id + ".jpg\">" +
                            "<ul class=\"product-description\">" +
                            "<li><strong>Rock Number: </strong>" + rock_id + "</li>" +
                            "<li><strong>Color: </strong>" + rs.getString("color") + "</li>" +
                            "<li><strong>Quantity Per Order: </strong>" + rs.getString("quantity_per_order") + "</li>" +
                            "<li><strong>Price Per Order: </strong>" + rs.getString("price_per_order") + "</li>" +
                            "<li><strong>Description: </strong>" + rs.getString("description") + "</li>" +
                            "</ul>" +
                            "<div id=\"cart-button-container\">" +
                            "<button id=\"add-to-cart\" type=\"button\" class=\"btn btn-primary\">Add to Cart</button>" +
                            "</div>"
                    );
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
}