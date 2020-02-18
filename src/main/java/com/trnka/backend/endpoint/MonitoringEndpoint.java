package com.trnka.backend.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface MonitoringEndpoint {

    @GetMapping(path = "alive")
    ResponseEntity<String> alive();

}
