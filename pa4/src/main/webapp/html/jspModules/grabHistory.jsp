<%@ page import="javax.ws.rs.core.MediaType" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.ws.rs.client.WebTarget" %>
<%@ page import="tylerkv.classes.RestClient" %>
<%@ page import="org.codehaus.jackson.type.TypeReference" %>
<%@ page import="org.codehaus.jackson.map.*" %>
<%@ page import="tylerkv.rest.model.Rock" %>


<%
    HttpSession customerSession = request.getSession(true);
    String historyKey = new String("history");
    LinkedList<String> historyList = null;
    
    if (customerSession.isNew()) {
        historyList = new LinkedList<String>();
        customerSession.setAttribute(historyKey, historyList);
    }
    else {
        historyList = (LinkedList<String>) customerSession.getAttribute(historyKey);
        if (historyList == null) {
            historyList = new LinkedList<String>();
        }
        
        Set<String> rocksInCart = new HashSet<String>();
        
        //constructing the query
        for(int i = 0; i < historyList.size(); i++ ) {
            rocksInCart.add(historyList.get(i));
        }
        
        //Start rest operations
        WebTarget target = RestClient.getClient();
        
        Iterator<String> it = rocksInCart.iterator();
        while(it.hasNext()) {
            String jsonResponse =
                    target.path("tylerkv").path("api").path("rocks").path(it.next()).
                    request().
                    accept(MediaType.APPLICATION_JSON).
                    get(String.class);
            
            ObjectMapper objectMapper = new ObjectMapper();
            
            Rock currentRock = objectMapper.readValue(jsonResponse, new TypeReference<Rock>(){}); %>
            
            <tr>
                <th scope="row"> <% out.println(currentRock.getRockId()); %> </th>
                <td>
                    <a href="html/details.jsp?rock_id=<% out.println(currentRock.getRockId());%>">
                        <img class="product-image hvr-grow" src="content/rock<% out.println(currentRock.getRockId()); %>.jpg">
                    </a>
                </td>
                <td><% out.println(currentRock.getColor()); %></td>
                <td><% out.println(currentRock.getQuantityPerOrder()); %></td>
                <td>$<% out.println(currentRock.getPricePerOrder()); %></td>
            </tr>            
            
<%            
        }
    }
    
%>
