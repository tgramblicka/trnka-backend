package com.trnka.backend.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.trnka.backend.control.DeviceEventControl;

@Produces({MediaType.APPLICATION_JSON}) @Consumes({MediaType.APPLICATION_JSON}) @Path("device") public class DeviceResImpl {

    @GET @Path("key-press/{keycode}")
    public Response event(@PathParam("keycode") String keycode) {
        new DeviceEventControl().deviceKeyPresses(keycode);
        return Response.status(Response.Status.OK).entity("keycode digested").build();
    }
}
