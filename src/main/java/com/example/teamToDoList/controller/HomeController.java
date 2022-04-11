package com.example.teamToDoList.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping ("/")
    public String homePage () {
        return "login";
    }


    @GetMapping("/signup")
    public String signupPage () {
        return "signup";
    }

    @GetMapping ("/listprofile")
    public String todolistprofile () {
        return "todolistprofile";

    }
}
