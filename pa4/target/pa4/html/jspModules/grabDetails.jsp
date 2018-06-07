<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="tylerkv.rest.db.DatabaseConnector" %>
<%@ page import="tylerkv.rest.db.DatabaseUtils" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.util.*" %>

<%
    String rock_id = request.getParameter("rock_id");
    if(rock_id != null) {
        Connection connection = DatabaseConnector.getConnection();
        String sql = "SELECT * FROM rocks WHERE rock_id = " + rock_id;
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, sql);
        
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
        
        if (resultSet != null) {
            try {
                while (resultSet.next()) { 
    %>
                    <img class="product-detail-image" src="../content/rock<%out.println(rock_id);%>.jpg">
                    <ul class="product-description">
                        <li><strong>Rock Number: </strong><%out.println(rock_id);%></li>
                        <li><strong>Color: </strong><%out.println(resultSet.getString("color"));%></li>
                        <li><strong>Quantity: </strong><%out.println(resultSet.getString("quantity_per_order"));%></li>
                        <li><strong>Price Per Order: </strong><%out.println(resultSet.getString("price_per_order"));%></li>
                        <li><strong>Description: </strong><%out.println(resultSet.getString("description"));%></li>
                    </ul>
                    <div id="cart-button-container">
                        <button id="add-to-cart" type="button" class="btn btn-primary">Add to Cart</button>
                    </div>
               <% }
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
    }

%>
