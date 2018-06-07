<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.util.*" %>
<%@ page import="tylerkv.classes.RestClient" %>
<%@ page import="javax.ws.rs.client.WebTarget" %>
<%@ page import="tylerkv.rest.model.Rock" %>
<%@ page import="org.codehaus.jackson.type.TypeReference" %>
<%@ page import="org.codehaus.jackson.map.*" %>
<%@ page import="javax.ws.rs.core.MediaType" %>

<%
    String rock_id = request.getParameter("rock_id");
    if(rock_id != null) {
        WebTarget target = RestClient.getClient();
        
        String jsonResponse =
                target.path("tylerkv").path("api").path("rocks").path(rock_id).
                request().
                accept(MediaType.APPLICATION_JSON).
                get(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
            
        Rock currentRock = objectMapper.readValue(jsonResponse, new TypeReference<Rock>(){});
        
        //customerSession handling
        HttpSession customerSession = request.getSession(true);
        String historyKey = new String("history");
        Queue<String> historyList = null;

        if (customerSession.isNew()) {
            historyList = new LinkedList<String>();
            historyList.add(rock_id);
            customerSession.setAttribute(historyKey, historyList);
        }
        else {
            historyList = (LinkedList<String>) customerSession.getAttribute(historyKey);
            if(historyList == null){
                historyList = new LinkedList<String>();
            }
            if(historyList.size() == 5){
                historyList.remove();
            }
            historyList.add(rock_id);
            customerSession.setAttribute(historyKey, historyList);
        }      
        
    %>
        <img class="product-detail-image" src="../content/rock<%out.println(rock_id);%>.jpg">
        <ul class="product-description">
            <li><strong>Rock Number: </strong><%out.println(rock_id);%></li>
            <li><strong>Color: </strong><%out.println(currentRock.getColor());%></li>
            <li><strong>Quantity: </strong><%out.println(currentRock.getQuantityPerOrder());%></li>
            <li><strong>Price Per Order: </strong><%out.println(currentRock.getPricePerOrder());%></li>
            <li><strong>Description: </strong><%out.println(currentRock.getDescription());%></li>
        </ul>
        <div id="cart-button-container">
            <button id="add-to-cart" type="button" class="btn btn-primary">Add to Cart</button>
        </div>
    <%
       }
        %>
