package tylerkv.rest.resource;

import tylerkv.rest.model.Rock;
import tylerkv.rest.service.RockService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericEntity;
import java.util.List;
//import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/rocks")
public class RockResource {
    
    @Path("{id}")
    @GET
    @Produces( { MediaType.APPLICATION_JSON})
    public Response getRockById(@PathParam("id") int id) {
        Rock rock = RockService.getRockById(id);
        
        if (rock == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok(rock).build();
    }
    
    @GET
    @Produces( { MediaType.APPLICATION_JSON})
    public Response getAllRocks() {
        List<Rock> rockList = RockService.getAllRocks();
        
        if (rockList == null || rockList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        GenericEntity<List<Rock>> rockListWrapper = new GenericEntity<List<Rock>>(rockList) {};
                
        return Response.ok(rockListWrapper).build();
    }
    
    @POST
    @Consumes( { MediaType.APPLICATION_JSON})
    public Response addRock(Rock rock) {
        if (RockService.AddRock(rock)) {
            return Response.ok().entity("Rock Added Successfully").build();
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    
    @POST
    @Consumes( { MediaType.APPLICATION_FORM_URLENCODED})
    public Response addRock(@FormParam("color") String color,
                            @FormParam("quantityPerOrder") String quantity_per_order,
                            @FormParam("pricePerOrder") String price_per_order,
                            @FormParam("description") String description) {
        Rock rock = new Rock();
        rock.setColor(color);
        rock.setQuantityPerOrder(Integer.parseInt(quantity_per_order));
        rock.setPricePerOrder(Float.valueOf(price_per_order));
        rock.setDescription(description);
        
        if(RockService.AddRock(rock)) {
            return Response.ok().entity("Rock Added Successfully").build();
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    
    @PUT
    @Path("{id}")
    @Consumes( { MediaType.APPLICATION_JSON})
    @Produces( { MediaType.APPLICATION_JSON})
    public Response updateRock(@PathParam("id") int id, Rock rock) {
        Rock retrievedRock = RockService.getRockById(id);
        
        if (retrievedRock == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity("We could not find the requested resource").build();
        }
        
        //Rock object retrieved from the json request
        rock.setRockId(id);
        
        //if the user has provided null, then we set the retrieved values
        // This is done so that a null value is not passed to the todo object when updating it.
        
        if (rock.getColor() == null) {
            rock.setColor(retrievedRock.getColor());
        } 
        
        if (rock.getQuantityPerOrder() < 1) {
            rock.setQuantityPerOrder(retrievedRock.getQuantityPerOrder());
        }
        
        if (rock.getPricePerOrder() < 1) {
            rock.setPricePerOrder(retrievedRock.getPricePerOrder());
        }
        
        if (rock.getDescription() == null) {
            rock.setDescription(retrievedRock.getDescription());
        }
        
        if(RockService.updateRock(rock)) {
            return Response.ok().entity(rock).build();
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    
    @DELETE
    @Path("{id}")
    @Consumes( { MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    public Response deleteRock(@PathParam("id") int id) {
        
        Rock retrievedRock = RockService.getRockById(id);
        
        if(retrievedRock == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity("We could not find the requested resource").build();
        }
        
        if(RockService.deleteRock(retrievedRock)) {
            return Response.ok().entity("Rock Deleted Successfully").build();
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    
}
