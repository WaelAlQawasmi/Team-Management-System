package com.example.teamToDoList.controller;


import com.example.teamToDoList.Repositories.*;
import com.example.teamToDoList.models.ToDoList;
import com.example.teamToDoList.models.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    PasswordEncoder passwordEncoder;

    private final UsersRepositorie usersRepositorie;
    private final  ToDoListRepositories  ToDoListRepositories;
    public HomeController(UsersRepositorie usersRepositorie, com.example.teamToDoList.Repositories.ToDoListRepositories toDoListRepositories) {
        this.usersRepositorie = usersRepositorie;
        ToDoListRepositories = toDoListRepositories;
    }

    @GetMapping ("/")
    public String homePage () {
        return "home";
    }


    @GetMapping("/signup")
    public String signupPage () {
       return "signup";
    }

    @PostMapping ("/signup")
    public RedirectView signupPost (@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String birthDay, @RequestParam String position, @RequestParam String address, @RequestParam String phone, @RequestParam String username, @RequestParam String password) {

        if (usersRepositorie.findByusername(username) == null) {
            String passwordEncoded = passwordEncoder.encode(password);
            Users users = new Users(firstName, lastName, email, birthDay, position, address, phone, username, passwordEncoded);
            usersRepositorie.save(users);
            return new RedirectView("/login");
        }else
        {
            return new RedirectView("/signup?taken=true");
        }
    }

    @GetMapping("/login")
    public String loginPage () {
        return "login";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }


        @GetMapping("/error")
        public String error () {
            return "error";

        }
        @GetMapping("/header")
        public String header () {
            return "header";

        }


        @GetMapping("/forgot")
        public String forgotPage () {
            return "forgot";
        }
        @GetMapping("/task")
        public String task () {
            return "myTask";

        }
@GetMapping("/myTask")
public String myTaskPage(Model model){
    return "myTask";
}
        @GetMapping("/profile")
        public String userProfile (Principal p, Model model) {
        Users newUser=usersRepositorie.findByusername(p.getName());
        model.addAttribute("userInfo",newUser);
            return "userProfile";

        }


///////////////////////////// add to do list/////////////////////////

    @GetMapping("/todolistname") //get
    public String toDoListName () {
        return "toDoListName";

    }


    @PostMapping ("/addtodo") //post
    public RedirectView addtodo (Principal p,@RequestParam String toDoListName) {

        Users newUser=usersRepositorie.findByusername(p.getName());
        ToDoList newList=new ToDoList(toDoListName);
        newList.setUsers(newUser);

ToDoListRepositories.save(newList);
newList.getId();
return new RedirectView("/listprofile?id="+newList.getId())  ;

    }

/////////////////to do list profile الرجاء عدم لمس اي شيء هنا ///////////////////////

    /***
     *
   please don't change anything here
   الرجاء عدم تغيير اي شيء هناااااااااااااااااااااا
     ***/


    @GetMapping ("/listprofile/{id}")
    public String toDoListProfile (Model model , @PathVariable Long id) {

        ToDoList toDoList=ToDoListRepositories.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("id",id);
        model.addAttribute("todomembers",toDoList.getMembers());

        return "todolistprofile";

    }



    @PostMapping ("/listprofile/adduser/{id}") // add user  on to do list
    public RedirectView adduser ( @PathVariable Long id, @RequestParam String username) {

    Users newUser=usersRepositorie.findByusername(username);

                  ToDoList toDoList=ToDoListRepositories.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));


                if(toDoList.getMembers().contains(newUser)) {
                    return new RedirectView("/listprofile/"+id+"?error=true")  ;
                }
        toDoList.getMembers().add(newUser);


        ToDoListRepositories.save(toDoList);

        return new RedirectView("/listprofile/"+id)  ;

    }

}
