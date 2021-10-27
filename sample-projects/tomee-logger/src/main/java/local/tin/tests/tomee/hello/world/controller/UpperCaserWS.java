package local.tin.tests.tomee.hello.world.controller;

import javax.interceptor.Interceptors;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import local.tin.tests.tomee.hello.world.interceptors.SimpleInterceptor;
import local.tin.tests.tomee.hello.world.services.UpperCaser;
import local.tin.tests.tomee.hello.world.services.UpperCaserImpl;

/**
 *
 * @author benitodarder
 */

@Interceptors(SimpleInterceptor.class)
@Path("/upperCaser")
public class UpperCaserWS {

    private final UpperCaser upperCaser = new UpperCaserImpl();
    
    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public Response transformGET(@QueryParam("source") String source) {

        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity(upperCaser.transform(source)).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response transformPOST(String source) {

        return Response.status(Response.Status.OK).type(MediaType.TEXT_PLAIN).entity(upperCaser.transform(source)).build();
    }
}
