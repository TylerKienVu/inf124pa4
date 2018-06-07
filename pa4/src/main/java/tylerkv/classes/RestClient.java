package tylerkv.classes;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.glassfish.jersey.client.ClientConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

public class RestClient {
    
    public static WebTarget getClient(){
        URI baseURI = UriBuilder.fromUri("http://localhost:8080/pa4").build();
        
        ClientConfig config = new ClientConfig();

        Client client = ClientBuilder.newClient(config);

        return client.target(baseURI);
    }
}
