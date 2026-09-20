package com.example.dex_gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/newQuartet")
    public String newQuartet() {
        return "newQuartet";
    }

    @GetMapping("/listQuartets")
    public String listQuartets() {
        return "listQuartets";
    }

    @GetMapping("/newUser")
    public String newUser() {
        return "newUser";
    }

    @GetMapping("/listUsers")
    public String listUsers() {
        return "listUsers";
    }

    @GetMapping("/manageUser")
    public String manageUser() {
        return "manageUser";
    }

    @GetMapping("/newExchange")
    public String newExchange() {
        return "newExchange";
    }

    @GetMapping("/manageExchange")
    public String manageExchange() {
        return "manageExchange";
    }

    @GetMapping("/listExchanges")
    public String listExchanges() {
        return "listExchanges";
    }

    @GetMapping("/automation")
    public String automation() {
        return "automation";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
