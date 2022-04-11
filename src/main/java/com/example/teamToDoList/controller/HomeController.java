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

    @GetMapping("/login")
    public String loginPage () {
        return "login";
    }

    @GetMapping ("/listprofile")
    public String todolistprofile () {
        return "todolistprofile";

    }
    @GetMapping ("/toDoListName")
    public String toDoListName () {
        return "toDoListName";

    }
    @GetMapping ("/error")
    public String error () {
        return "error";

    }
    @GetMapping ("/header")
    public String header () {
        return "header";

    }

    @GetMapping ("/forgot")
    public String forgotPage () {
        return "forgot";
    }
}
