package tylerkv.servlets;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tylerkv.rest.model.Order;
import org.codehaus.jackson.map.ObjectMapper;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;
import tylerkv.classes.RestClient;
import javax.ws.rs.core.Response;

public class CreateOrder extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException{
        doPost(req,res);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        
        //grab cart
        HttpSession session = request.getSession(true);
        String cartKey = new String("cart");
        List<String> cartList = null;
        cartList = (List<String>) session.getAttribute(cartKey);

        if(cartList == null){
            return;
        }        
        String cart_string = "";
        for(int i = 0; i < cartList.size(); i++){
            String rock_id = cartList.get(i);
            cart_string += rock_id;
        }        
        
        //construct Order object
        Order order = new Order();
        
        order.setFname(request.getParameter("fname"));
        order.setLname(request.getParameter("lname"));
        order.setEmail(request.getParameter("email"));
        order.setPhone(request.getParameter("phone"));
        order.setShipAddress(request.getParameter("shipAddress"));
        order.setShipAddress2(request.getParameter("shipAddress2"));
        order.setCity(request.getParameter("city"));
        order.setState(request.getParameter("state"));
        order.setZip(request.getParameter("zip"));
        order.setCardName(request.getParameter("cardName"));
        order.setCardNumber(request.getParameter("cardNumber"));
        order.setExpiration(request.getParameter("expiration"));
        order.setCsc(request.getParameter("csc"));
        order.setBillAddress(request.getParameter("billAddress"));
        order.setBillAddress2(request.getParameter("billAddress2"));
        order.setBillCity(request.getParameter("billCity"));
        order.setBillState(request.getParameter("billState"));
        order.setBillZip(request.getParameter("billZip"));
        order.setCartString(cart_string);
        
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(order);
        out.println(jsonString);
        
        WebTarget target = RestClient.getClient();
        
        Response postResponse = target.path("tylerkv").path("api").path("order").
                request(MediaType.APPLICATION_JSON).
                post(Entity.entity(order, MediaType.APPLICATION_JSON));
        
        out.println(postResponse);
             
        //forward to orderDetails
        RequestDispatcher rd = request.getRequestDispatcher("html/receipt.jsp");
        rd.forward(request,response);

        out.flush();
    }
}
