<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.ws.rs.client.WebTarget" %>
<%@ page import="javax.ws.rs.core.MediaType" %>
<%@ page import="org.codehaus.jackson.map.*" %>
<%@ page import="tylerkv.classes.RestClient" %>
<%@ page import="tylerkv.rest.model.Rock" %>
<%@ page import="java.util.List" %>
<%@ page import="org.codehaus.jackson.type.TypeReference" %>


<%
    WebTarget target = RestClient.getClient();
    
    String jsonResponse =
            target.path("tylerkv").path("api").path("rocks").
            request().
            accept(MediaType.APPLICATION_JSON).
            get(String.class);
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    List<Rock> rockList = objectMapper.readValue(jsonResponse, new TypeReference<List<Rock>>(){});
    
    for (Rock rock : rockList) { %>
    <tr>
        <th scope="row"><%out.println(rock.getRockId());%></th>
        <td>
            <a href="html/details.jsp?rock_id=<%out.println(rock.getRockId());%>">
                <img class="product-image hvr-grow" src="content/rock<%out.println(rock.getRockId());%>.jpg">
            </a>
        </td>
        <td><%out.println(rock.getColor());%></td>
        <td><%out.println(rock.getQuantityPerOrder());%></td>
        <td><%out.println(rock.getPricePerOrder());%></td>
    </tr>
<%
    }  
%>
