<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="tylerkv.rest.db.DatabaseConnector" %>
<%@ page import="tylerkv.rest.db.DatabaseUtils" %>
<%@ page import="tylerkv.classes.Tuple" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.util.*" %>


<%
    Connection connection = DatabaseConnector.getConnection();
    HttpSession customerSession = request.getSession(true);
    String cartKey = new String("cart");
    List<String> cartList = null;
    ResultSet rs = null;
    Map<String, Tuple<Integer,Float>> cartTable = new HashMap<String, Tuple<Integer,Float>>();


    cartList = (List<String>) customerSession.getAttribute(cartKey);
    if(cartList == null){
        cartList = new ArrayList<String>();
        customerSession.setAttribute(cartKey,cartList);
    }

    //Fills dictionary cartTable
    //key = rock_id
    //value = (count, totalPrice)
    for(int i = 0; i < cartList.size(); i++){
        String rock_id = cartList.get(i);
        String sql = "SELECT * FROM rocks WHERE rock_id = " + rock_id;
        rs = DatabaseUtils.retrieveQueryResults(connection, sql);
        if(rs.next()){
            if (cartTable.containsKey(rock_id)){
                Tuple<Integer,Float> retrievedTuple = cartTable.get(rock_id);
                retrievedTuple.setX(retrievedTuple.getX() + 1);
                retrievedTuple.setY(retrievedTuple.getY() + rs.getFloat("price_per_order"));
                cartTable.put(rock_id, retrievedTuple);
            }
            else {
                Tuple<Integer,Float> tupleToInsert = new Tuple<Integer,Float>(1,rs.getFloat("price_per_order")); 
                cartTable.put(rock_id,tupleToInsert);
            }
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
                  $<% out.println(total); %>
            </div>    
        </div>   
    
<%
    out.flush();    
%>
