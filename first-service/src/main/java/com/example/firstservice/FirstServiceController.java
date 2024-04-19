package com.example.firstservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.Iterator;

@RestController
@RequestMapping("/first-service")
@Slf4j
@RequiredArgsConstructor
public class FirstServiceController {

    private final Environment env;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the First service.";
    }

    @GetMapping("/message")
    public String message(@RequestHeader(name = "first-request",required = false) String header) {

        log.info("header = {}", header);
        return "Hello World in First Service + "+header;
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port ={}", request.getServerPort());
        return "This is a message from Second Service on PORT"+ env.getProperty("local.server.port");
    }
}
