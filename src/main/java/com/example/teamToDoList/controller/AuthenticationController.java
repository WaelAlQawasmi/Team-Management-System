package com.example.teamToDoList.controller;

import com.example.teamToDoList.Repositories.PostRepostries;
import com.example.teamToDoList.Repositories.UsersRepositorie;
import com.example.teamToDoList.models.ToDoList;
import com.example.teamToDoList.models.ToDoListItems;
import com.example.teamToDoList.models.Users;
import com.example.teamToDoList.models.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AuthenticationController {
    @Autowired
    PasswordEncoder passwordEncoder;

    private com.example.teamToDoList.Repositories.ToDoListItemsRepositories ToDoListItemsRepositories;
    private UsersRepositorie usersRepositorie ;
    private com.example.teamToDoList.Repositories.ToDoListRepositories ToDoListRepositories;
    private final PostRepostries postRepositories;
    public AuthenticationController(UsersRepositorie usersRepositorie, com.example.teamToDoList.Repositories.ToDoListRepositories toDoListRepositories, com.example.teamToDoList.Repositories.ToDoListRepositories toDoListRepositoriesl, com.example.teamToDoList.Repositories.ToDoListItemsRepositories toDoListItemsRepositories, PostRepostries postRepositories) {
        this.usersRepositorie = usersRepositorie;
        ToDoListRepositories = toDoListRepositories;
        ToDoListItemsRepositories = toDoListItemsRepositories;
        this.postRepositories = postRepositories;
    }

    // Dashboard Route
    // To Get All To-do List That The Current User Admin On it
    @GetMapping("/dashboard")
    public  String Dashboard( Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();// Another Way To Get Authenticated User
        Users user=usersRepositorie.findByusername(authentication.getName());// To Find The Name of User
        model.addAttribute("todolist",user.getTodolists());
        model.addAttribute("del",true);
        model.addAttribute("username",user.getUsername());
        //
        List<ToDoListItems> items= ToDoListItemsRepositories.findMytask("0",user.getId());
        model.addAttribute("number",items.size());
        model.addAttribute("todomembers",items);
        //
        return "myTask";
    }

    // To Get All To-do List That The Current User member on it
    @GetMapping("/myTask")
    public String myTaskPage(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users user=usersRepositorie.findByusername(authentication.getName());
        model.addAttribute("todolist",user.getTasks());
        model.addAttribute("del",false);
        model.addAttribute("username",user.getUsername());
        //
        List<ToDoListItems> items= ToDoListItemsRepositories.findMytask("0",user.getId());
        model.addAttribute("number",items.size());
        model.addAttribute("todomembers",items);
        //
        return "myTask";
    }

    //To Get All Information About The Current User
    @GetMapping("/profile")
    public  String userProfile(Principal p, Model model) {
        Users newUser=usersRepositorie.findByusername(p.getName());
        newUser.getToDoListItems();
        model.addAttribute("userInfo",newUser);
        return "userProfile";

    }

    //To Get All Information About a Specific Users
    @GetMapping("/profile/{id}")
    public  String userProfileById(@PathVariable Long id, Model model) {
        Users newUser=usersRepositorie.finduserById(id);
        model.addAttribute("userInfo",newUser);
        return "userProfile";

    }

    // To Add Comment On To-do List
    @PostMapping("/listprofile/addComment/{id}")
    public RedirectView addComment (Principal p, @RequestParam String comment, @PathVariable Long id) {
        Users newUser=usersRepositorie.findByusername(p.getName());
       //git local time with format
        ToDoList toDoList=ToDoListRepositories.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
     //
        post post=new post();
        post.setComment(comment);
        post.setTime(formattedDate);
        post.setUsersmember(newUser);
        post.setTodolist(toDoList);
        postRepositories.save(post);
        return new RedirectView("/listprofile/"+id);
    }


    // To Add User On To-do List
    @PostMapping ("/listprofile/adduser/{id}")
    public RedirectView adduser ( @PathVariable Long id, @RequestParam String username) {

        Users newUser=usersRepositorie.findByusername(username);

        if(newUser==null)
        {
            return new RedirectView("/listprofile/"+id+"?nooneerror=true")  ;
        }

        ToDoList toDoList=ToDoListRepositories.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));


        if(toDoList.getMembers().contains(newUser)) {
            return new RedirectView("/listprofile/"+id+"?error=true")  ;
        }
        toDoList.getMembers().add(newUser);
        ToDoListRepositories.save(toDoList);

        return new RedirectView("/listprofile/"+id+"?adddone=one")  ;
    }

    // To Add Task On To-do List
    @PostMapping ("/listprofile/addtask/{listId}") // add task  on to do list
    public RedirectView addTask (Principal p , @PathVariable Long listId, @RequestParam String username , @RequestParam String task, @RequestParam String description) {
        // 1. Find The Current User
        Users member = usersRepositorie.findByusername(username);

        // 2. Find Current To-do List
        ToDoList toDoList = ToDoListRepositories.findById(listId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + listId));

        // 3. Find The Admin Of To-do List
        Users admin = usersRepositorie.findByusername(p.getName());

        // 4. To Check Before Adding the Task For User If The User was Member or Admin On The To-do List
        if (toDoList.getMembers().contains(member) || toDoList.getUsers().equals(admin)) {
            // 5. To Check If The Current User Is Admin Of The To-do List
            if (toDoList.getUsers().getId() == admin.getId()) {

                if (toDoList.getMembers().contains(member) || toDoList.getUsers().getId() == member.getId()) {
                    ToDoListItems newItem1 = new ToDoListItems(task, "0", description);

                    newItem1.setTodolist(toDoList);
                    newItem1.setUsersmember(member);

                    ToDoListItemsRepositories.save(newItem1);

                    return new RedirectView("/listprofile/" + listId + "?adddone=on2e");
                }
                return new RedirectView("/listprofile/" + listId + "?errorone=1");
            }
        }
        return new RedirectView("/listprofile/" + listId + "?eradmin=1");
    }

    // To Get All The Task For Current User
    @GetMapping ("/taskslist")
    public String taskDescription (Principal p, Model model) {
        Users newUser = usersRepositorie.findByusername(p.getName());
        List<ToDoListItems> todolist =  ToDoListItemsRepositories.findByusersmember_id( newUser.getId());

        model.addAttribute("TaskDescriptionList", todolist);
        return "tasksList";
    }

    // To Notify The Current User That There is a Request To Join For A New To-do List
    @GetMapping ("/notifications")
    public String notifications (Principal p,Model model ) {
        Users user = usersRepositorie.findByusername(p.getName());
        List<ToDoListItems> items= ToDoListItemsRepositories.findMytask("0",user.getId());

        if (items.size() == 0) {
            model.addAttribute("notEmpty", false);
            model.addAttribute("isEmpty", true);
        } else {
            model.addAttribute("isEmpty", false);
            model.addAttribute("notEmpty", true);
        }


        model.addAttribute("number",items.size());
        model.addAttribute("todomembers",items);
        return "notfecation";

    }

}
