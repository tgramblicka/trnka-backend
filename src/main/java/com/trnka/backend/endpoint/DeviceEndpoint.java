package com.trnka.backend.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces({MediaType.APPLICATION_JSON })
@Consumes({MediaType.APPLICATION_JSON })
@Path("device")
public interface DeviceEndpoint {

    @GET
    @Path("key-press/{keycode}")
    Response event(@PathParam("keycode") String keycode);

}
