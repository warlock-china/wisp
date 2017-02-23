package cn.com.warlock.wisp.router.rest.test.handler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/wisp")
public class EntryPointTestHandler {

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        return "Test";
    }
}
