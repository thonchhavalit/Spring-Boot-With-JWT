package com.example.springbootjwt.controller;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@Slf4j
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @GetMapping("/home")
    public String homePage(Authentication authentication){
        var user = authentication.getPrincipal();
        logger.info("User is : {}",authentication.getPrincipal());
        logger.info("User is : {}",authentication.getCredentials());
        logger.info("User is : {}",authentication.getDetails());
        logger.info("User is : {}",authentication.getAuthorities());
        return "Hello "+authentication.getName();
    }
}
