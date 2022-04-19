package com.example.teamToDoList.controller;

import com.example.teamToDoList.Repositories.UsersRepositorie;
import com.example.teamToDoList.models.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UnAuthenticationController {

    //Password Encoder
    private final PasswordEncoder passwordEncoder;

    //Repository
    private final UsersRepositorie usersRepositorie;

    public UnAuthenticationController(PasswordEncoder passwordEncoder, UsersRepositorie usersRepositorie) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepositorie = usersRepositorie;
    }




    //Root Route
    @GetMapping("/")
    public static String homePage() {
        return "home";
    }

    //Get SignUp Route
    @GetMapping("/signup")
    public static String signupPage() {
        return "signup";
    }

    //Post SignUp Route
    @PostMapping("/signup")
    public RedirectView signupPost(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String birthDay, @RequestParam String position, @RequestParam String address, @RequestParam String phone, @RequestParam String username, @RequestParam String password) {

        if (usersRepositorie.findByusername(username) == null) {
            String passwordEncoded = passwordEncoder.encode(password);
            Users users = new Users(firstName, lastName, email, birthDay, position, address, phone, username, passwordEncoded);
            usersRepositorie.save(users);
            return new RedirectView("/login");
        } else {
            return new RedirectView("/signup?taken=true");
        }
    }

    //Git Login Route
    @GetMapping("/login")
    public static String loginPage() {
        return "login";
    }

    //Error Route
    @GetMapping("/error")
    public String error() {
        return "error";

    }
}
