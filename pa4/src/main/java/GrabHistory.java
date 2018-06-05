import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/history")
public class GrabHistory extends HttpServlet{
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
            String historyKey = new String("history");
            LinkedList<String> historyList = null;
            String historyQueryString = "(";
            
            if (session.isNew()) {
//                out.println("new session");
                historyList = new LinkedList<String>();
                session.setAttribute(historyKey, historyList);
            }
            else {
//                out.println("old session");
                historyList = (LinkedList<String>) session.getAttribute(historyKey);
                if(historyList == null){
                    historyList = new LinkedList<String>();
                }
                for(int i=0; i < historyList.size(); i++){
                    if(i == 0){
                        historyQueryString += historyList.get(i);
                    }
                    else {
                        String stringToAdd = "," + historyList.get(i);
                        historyQueryString += stringToAdd;
                    }
                }
            }
            historyQueryString += ")";
            if(!historyQueryString.equals("()")){
                rs = stmt.executeQuery("SELECT * FROM rocks where rock_id IN " + historyQueryString);
                while(rs.next()){
                    out.println("<tr>" + 
                            "<th scope=\"row\">"+ rs.getString("rock_id") +"</th>" +
                            "<td><a href=\"html/details.jsp?rock_id=" + rs.getString("rock_id") + "\">" +
                            "<img class=\"product-image hvr-grow\" src=\"content/rock" + rs.getString("rock_id") + ".jpg\"></a></td>" +
                            "<td>" + rs.getString("color") + "</td>" +
                            "<td>" + rs.getString("quantity_per_order") + "</td>" +
                            "<td>$" + rs.getString("price_per_order") + "</td>" +
                            "</tr>");
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
