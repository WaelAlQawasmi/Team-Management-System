<<<<<<< HEAD

=======
package com.example.teamToDoList;

import com.example.teamToDoList.Repositories.UsersRepositorie;
import com.example.teamToDoList.controller.HomeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamToDoListApplicationTests{
    @Autowired
    UsersRepositorie usersRepositorie;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webapplicationContext;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webapplicationContext).build();
    }
    @Test
    void contextLoads() {
    }
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
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @Test
    public void HomePageTest() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Home")
                .contains("about")
                .contains("Login")
                .contains("Team organizer")
               .contains("created by");

    }
    @Test
    public void LoginPageTest() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/login",
                String.class)).contains("Login")
                .contains("Enter your Username")
                .contains("Enter your Password")
                .contains("Forgot password")
                .contains("submit");

    }
    @Test
    public void signPageTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/signup",
                String.class)).contains("Signup")
                .contains("First Name")
                .contains("Last Name")
                .contains("Enter Your Email")
                .contains("Already have an account")
                .contains("Your Phone")
                .contains("Login now");

    }
 

}

>>>>>>> refs/remotes/origin/main
