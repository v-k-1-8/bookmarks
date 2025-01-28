package com.progskipper.bookmarks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    ///  Public Health Check controller ensuring functionality of server

    @GetMapping("health-check")
    public String healthCheck() {
        return "Health Ok";
    }
}
