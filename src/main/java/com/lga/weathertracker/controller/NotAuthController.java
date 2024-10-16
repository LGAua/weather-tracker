package com.lga.weathertracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/not-authorised")
public class NotAuthController {

    @GetMapping
    public String unauthorisedAccess(){
        return "not-authorised";
    }
}
