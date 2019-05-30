package com.trnka.backend.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Produces({MediaType.APPLICATION_JSON })
@Consumes({MediaType.APPLICATION_JSON })
@Path("monitoring")
public interface MonitoringEndpoint {

    @GET
    @Path("alive")
    Response alive();

}
