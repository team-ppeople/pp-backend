package com.pp.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class DefaultController {

    @GetMapping(value = "/api/v1/healthcheck")
    public ResponseEntity<?> healthcheck() {
        return ok("OK");
    }

    @GetMapping(value = "/api/v1/permitall")
    public ResponseEntity<?> permitall() {
        return ok("permitall");
    }

    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/api/v1/authenticated")
    public ResponseEntity<?> authenticated() {
        return ok("authenticated");
    }

}
