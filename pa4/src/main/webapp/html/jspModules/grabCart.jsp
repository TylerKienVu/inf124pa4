<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="tylerkv.classes.Tuple" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.util.*" %>
<%@ page import="tylerkv.classes.RestClient" %>
<%@ page import="javax.ws.rs.client.WebTarget" %>
<%@ page import="javax.ws.rs.core.MediaType" %>
<%@ page import="org.codehaus.jackson.type.TypeReference" %>
<%@ page import="org.codehaus.jackson.map.*" %>
<%@ page import="tylerkv.rest.model.Rock" %>


<%
    HttpSession customerSession = request.getSession(true);
    String cartKey = new String("cart");
    List<String> cartList = null;
    Map<String, Tuple<Integer,Float>> cartTable = new HashMap<String, Tuple<Integer,Float>>();


    cartList = (List<String>) customerSession.getAttribute(cartKey);
    if(cartList == null){
        cartList = new ArrayList<String>();
        customerSession.setAttribute(cartKey,cartList);
    }

    //Fills dictionary cartTable
    //key = rock_id
    //value = (count, totalPrice)
    WebTarget target = RestClient.getClient();
    for(int i = 0; i < cartList.size(); i++){
        String rock_id = cartList.get(i);
        
        String jsonResponse =
                target.path("tylerkv").path("api").path("rocks").path(rock_id).
                request().
                accept(MediaType.APPLICATION_JSON).
                get(String.class);        
        
        ObjectMapper objectMapper = new ObjectMapper();
            
        Rock currentRock = objectMapper.readValue(jsonResponse, new TypeReference<Rock>(){});        

        if (cartTable.containsKey(rock_id)){
            Tuple<Integer,Float> retrievedTuple = cartTable.get(rock_id);
            retrievedTuple.setX(retrievedTuple.getX() + 1);
            retrievedTuple.setY(retrievedTuple.getY() + currentRock.getPricePerOrder());
            cartTable.put(rock_id, retrievedTuple);
        }
        else {
            Tuple<Integer,Float> tupleToInsert = new Tuple<Integer,Float>(1,currentRock.getPricePerOrder()); 
            cartTable.put(rock_id,tupleToInsert);
        }
    }
    
    Iterator it = cartTable.entrySet().iterator();
    float total = 0;
    while (it.hasNext()){
        Map.Entry pair = (Map.Entry)it.next();
        String key = (String) pair.getKey();
        Tuple<Integer,Float> value = (Tuple<Integer,Float>) pair.getValue();
        total += value.getY();
%>
        
        <tr>
            <th><%out.println(key);%></th>
            <td><%out.println(value.getX()); %></td>
            <td><%out.println(value.getY());%></td>
        </tr>       

<%        
    } %>
    
        <div id="price-container">
            <h5>Total Price</h5>
            <div class="price-display">
                <strong>$<% out.println(total); %></strong>
            </div>    
        </div>   
    
<%
    out.flush();    
%>
