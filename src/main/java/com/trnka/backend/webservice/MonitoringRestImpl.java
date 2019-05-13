package com.trnka.backend.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.trnka.backend.endpoint.MonitoringEndpoint;

@Path("monitoring")
@Produces({MediaType.APPLICATION_JSON })
@Consumes({MediaType.APPLICATION_JSON })
public class MonitoringRestImpl implements MonitoringEndpoint {

    public MonitoringRestImpl() {
        super();
    }

    @GET
    @Path("alive")
    @Override
    public Response alive() {
        return Response.ok("im alive").build();
    }

}
