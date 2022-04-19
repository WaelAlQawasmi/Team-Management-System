
package com.example.teamToDoList;

import com.example.teamToDoList.Repositories.UsersRepositorie;
import com.example.teamToDoList.controller.ToDoListController;
import com.example.teamToDoList.controller.UnAuthenticationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamToDoListApplicationTests{
    @Autowired
    UsersRepositorie usersRepositorie;

    private MockMvc mockMvc;
@Autowired
    private ObjectMapper mapper ;
@Autowired
    private WebApplicationContext context;
    @Mock
    private Principal principal;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    void contextLoads() {
    }
    @Test
    public void homepage() {
        String results = UnAuthenticationController.homePage();
        assertEquals("home", results);
    }

    @Test
    public void signup() {
        String results = UnAuthenticationController.signupPage();
        assertEquals("signup", results);
    }
    @Test
    public void login() {
        String results = UnAuthenticationController.loginPage();
        assertEquals("login", results);
    }


    @Test
    public void userProfile() {
        String results = ToDoListController.toDoListName();
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
    @Test
    public void testHeader()throws Exception{

//        String jsonRequest=mapper.writeValueAsString(user);
        MvcResult result=mockMvc.perform(get("/header")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200,result.getResponse().getStatus());
    }
    @Test
    public void testunauthheader()throws Exception{

        MvcResult result=mockMvc.perform(get("/unauthheader")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200,result.getResponse().getStatus());
    }
    @Test
    public void forgotPasswordTest()throws Exception{

        MvcResult result=mockMvc.perform(get("/forgot")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200,result.getResponse().getStatus());
    }
    @Test
    public void todolistnameTest()throws Exception{

        MvcResult result=mockMvc.perform(get("/todolistname")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200,result.getResponse().getStatus());
    }
    @Test
    public void addUserTest()throws Exception{
        // u have to ensure that username is exist in database
        String username="ew";
        // u have to ensure that todolist id exists and match with user id
        int id=1;
        MvcResult result=mockMvc.perform(post("/listprofile/adduser/{id}",id)
                .param("username",username).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302)).andReturn();
        assertEquals(302,result.getResponse().getStatus());
    }
    @Test
    public void signupNewUserTest()throws Exception{

        MvcResult result=mockMvc.perform(post("/signup")
                        .param("firstName","ahmad")
                        .param("lastName","alfaiq")
                        .param("email","io.gmail.com")
                        .param("birthDay","1/2/2022")
                        .param("position","cs")
                        .param("address","amman")
                        .param("phone","0777777777")
                        .param("username","test")
                        .param("password","1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302)).andReturn();
        assertEquals(302,result.getResponse().getStatus());
    }
    @Test
    public void myTaskTest()throws Exception{
        // u have to ensure that username is exist in database
        String username="ew";
        String password="123";
        ResponseEntity<?> response= restTemplate.withBasicAuth(username,password).getForEntity("/myTask",String.class);


        assertEquals(200,response.getStatusCodeValue());
    }
    @Test
    public void profileTest()throws Exception{
        // u have to ensure that username is existed in database
        // u have to ensure that id is existed in database
        String username="ew";
        String password="123";
        ResponseEntity<?> response= restTemplate.withBasicAuth(username,password).getForEntity("/profile/{id}",String.class,1);


        assertEquals(200,response.getStatusCodeValue());
    }
    @Test
    public void addUseest()throws Exception{
        // u have to ensure that username is exist in database
        String toDoListName="sample";
        String username="ew";
        MvcResult result=mockMvc.perform(post("/addtodo")
                        .param("toDoListName",toDoListName)
                        .param("username",username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302)).andReturn();
        assertEquals(302,result.getResponse().getStatus());
    }
    @Test
    public void listProfileTdest()throws Exception{
        // u have to ensure that username is existed in database
        // u have to ensure that id is existed in database
String username="ew";
String password="123";
        ResponseEntity<?> response= restTemplate.withBasicAuth(username,password).getForEntity("/listprofile/{id}",String.class,1);


        assertEquals(200,response.getStatusCodeValue());
    }
    @Test
    public void addCommentTest()throws Exception{
        // u have to ensure that username is existed in database
        // u have to ensure that id is existed in database
        String username="ew";
        String password="123";
        Map<String, String> uriVariables = new HashMap<>();

        uriVariables.put("id", "1");
        uriVariables.put("comment", "hello");
        ResponseEntity<?> response= restTemplate.withBasicAuth(username,password).postForEntity("/listprofile/addComment/{id}", HttpMethod.POST,String.class,uriVariables);


        assertEquals(302,response.getStatusCodeValue());
    }
    @Test
    public void listProlTdest()throws Exception{
        // u have to ensure that username is existed in database
        // u have to ensure that id is existed in database
        String username="ew";
        String password="123";
        Map<String, String> uriVariables = new HashMap<>();

        uriVariables.put("id", "1");
        uriVariables.put("comment", "hello");
        ResponseEntity<?> response= restTemplate.withBasicAuth(username,password).postForEntity("/listprofile/addComment/{id}", HttpMethod.POST,String.class,uriVariables);


        assertEquals(302,response.getStatusCodeValue());
    }
    @Test
    public void listProlest()throws Exception{
        // u have to ensure that username is existed in database
        // u have to ensure that id is existed in database
        String username="ew";
        String password="123";
        Map<String, String> uriVariables = new HashMap<>();

        uriVariables.put("listId", "1");
        uriVariables.put("comment", "hello");
        uriVariables.put("username",username);
        uriVariables.put("task","test");
        uriVariables.put("description","test_describtion");

        ResponseEntity<?> response= restTemplate.withBasicAuth(username,password).postForEntity("/listprofile/addtask/{listId}", HttpMethod.POST,String.class,uriVariables);


        assertEquals(302,response.getStatusCodeValue());
    }
    @Test
    public void taskListTest()throws Exception{
        // u have to ensure that username is existed in database
        // u have to ensure that id is existed in database
        String username="ew";
        String password="123";

        ResponseEntity<?> response= restTemplate.withBasicAuth(username,password).getForEntity("/taskslist",String.class);



        assertEquals(200,response.getStatusCodeValue());
    }
    @Test
    public void notificationTest()throws Exception{
        // u have to ensure that username is existed in database
        // u have to ensure that id is existed in database
        String username="ew";
        String password="123";

        ResponseEntity<?> response= restTemplate.withBasicAuth(username,password).getForEntity("/notifications",String.class);


        assertEquals(200,response.getStatusCodeValue());
    }
    @Test
    public void deleteItem()throws Exception{
        // u have to ensure that username is existed in database
        // u have to ensure that id is existed in database
        String username="ew";
        String password="123";
        Map<String, String> uriVariables = new HashMap<>();

        uriVariables.put("ToDoId", "1");
        uriVariables.put("id", "2");
        ResponseEntity<?> response= restTemplate.withBasicAuth(username,password).postForEntity("/listprofile/{ToDoId}", HttpMethod.POST,String.class,uriVariables);


        assertEquals(302,response.getStatusCodeValue());
    }

@Test
public void requistAndAprove()throws Exception{
    // u have to ensure that username is existed in database
    // u have to ensure that id is existed in database
    String username="ew";
    String password="123";
    Map<String, String> uriVariables = new HashMap<>();

    uriVariables.put("status", "accept");
    uriVariables.put("taskid", "1");
    uriVariables.put("todoid", "1");
    ResponseEntity<?> response= restTemplate.withBasicAuth(username,password).postForEntity("/taskstatus/{status}/{taskid}", HttpMethod.POST,String.class,uriVariables);


    assertEquals(302,response.getStatusCodeValue());
}
}

