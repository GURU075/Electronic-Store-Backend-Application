package com.guru.electronic.strore.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @RequestMapping("/test")
    public String testing(){
        return "Welcome to elctronic store";
    }
}
