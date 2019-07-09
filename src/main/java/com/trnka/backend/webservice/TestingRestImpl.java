package com.trnka.backend.webservice;

import com.trnka.restapi.dto.TestDto;
import com.trnka.restapi.endpoint.TestingGetEndpoint;

import javax.ejb.Stateless;
import javax.ws.rs.PathParam;

@Stateless public class TestingRestImpl implements TestingGetEndpoint {

    public TestDto getTest(@PathParam("studentId") final String s) {
        return new TestDto();
    }
}
