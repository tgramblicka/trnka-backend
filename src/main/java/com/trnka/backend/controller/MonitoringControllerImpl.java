package com.trnka.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trnka.backend.endpoint.MonitoringEndpoint;

@RestController
@RequestMapping(RestApiPaths.PATH_MONITORING)
public class MonitoringControllerImpl implements MonitoringEndpoint {
    @Override
    public ResponseEntity<String> alive() {
        return new ResponseEntity<>("Im alive",
                                    HttpStatus.OK);
    }
}
