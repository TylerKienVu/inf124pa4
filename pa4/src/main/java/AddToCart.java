import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/cart")
public class AddToCart extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException{
        doGet(req,res);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rock_id = request.getParameter("rock_id");
        PrintWriter out = response.getWriter();
        try{
            HttpSession session = request.getSession(true);
            Date createTime = new Date(session.getCreationTime());
            String cartKey = new String("cart");
            List<String> cartList = null;
            
            if (session.isNew()) {
                cartList = new ArrayList<String>();
                cartList.add(rock_id);
                session.setAttribute(cartKey, cartList);
            }
            else {
                cartList = (List<String>) session.getAttribute(cartKey);
                if(cartList == null){
                    cartList = new ArrayList<String>();
                }
                cartList.add(rock_id);
                session.setAttribute(cartKey, cartList);
                out.println(cartList.size());
            }
            
            out.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
