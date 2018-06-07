package tylerkv.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tylerkv.rest.model.Order;
import tylerkv.rest.service.OrderService;

@Path("/order")
public class OrderResource {
    @POST
    @Consumes( { MediaType.APPLICATION_JSON})
    public Response addOrder(Order order) {
        if (OrderService.AddOrder(order)) {
            return Response.ok().entity("Order Added Successfully").build();
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
