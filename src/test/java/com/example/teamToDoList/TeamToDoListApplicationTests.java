package com.example.teamToDoList;

import com.example.teamToDoList.Repositories.UsersRepositorie;
import com.example.teamToDoList.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeamToDoListApplicationTests{
    @Test
    public void homepage() {
        String results = HomeController.homePage();
        assertEquals("home", results);
    }
    @Test
    public void signup() {
        String results = HomeController.signupPage();
        assertEquals("signup", results);
    }
    @Test
    public void login() {
        String results = HomeController.loginPage();
        assertEquals("login", results);
    }
    @Test
    public void header() {
        String results = HomeController.header();
        assertEquals("header", results);
    }
    @Test
    public void unAuthHeader() {
        String results = HomeController.unAuthHeader();
        assertEquals("unauthheader", results);
    }
    @Test
    public void forgotPage() {
        String results = HomeController.forgotPage();
        assertEquals("forgot", results);
    }

    @Test
    public void userProfile() {
        String results = HomeController.toDoListName();
        assertEquals("toDoListName", results);
    }
    @Autowired
    UsersRepositorie usersRepositorie;

    private MockMvc mockMvc;

}