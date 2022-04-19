package com.example.teamToDoList.controller;


import com.example.teamToDoList.Repositories.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    PasswordEncoder passwordEncoder;

    private  ToDoListItemsRepositories  ToDoListItemsRepositories;
    private  UsersRepositorie usersRepositorie ;
    private ToDoListRepositories  ToDoListRepositories;
    private final PostRepostries postRepositories;
    public HomeController(UsersRepositorie usersRepositorie, com.example.teamToDoList.Repositories.ToDoListRepositories toDoListRepositories, com.example.teamToDoList.Repositories.ToDoListRepositories toDoListRepositoriesl, com.example.teamToDoList.Repositories.ToDoListItemsRepositories toDoListItemsRepositories, PostRepostries postRepositories) {
        this.usersRepositorie = usersRepositorie;
        ToDoListRepositories = toDoListRepositories;
        ToDoListItemsRepositories = toDoListItemsRepositories;
        this.postRepositories = postRepositories;
    }



    @GetMapping ("/dashboard")
    public  String Dashboard(Principal p ,Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users user=usersRepositorie.findByusername(authentication.getName());
        model.addAttribute("todolist",user.getTodolists());
        model.addAttribute("del",true);
        //
        List<ToDoListItems> items= ToDoListItemsRepositories.findMytask("0",user.getId());
        model.addAttribute("number",items.size());
        model.addAttribute("todomembers",items);
        //
        return "myTask";
    }

    @GetMapping ("/")
    public static String homePage() {
        return "home";
    }


    @GetMapping("/signup")
    public static String signupPage() {
       return "signup";
    }

    @PostMapping ("/signup")
    public  RedirectView signupPost(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String birthDay, @RequestParam String position, @RequestParam String address, @RequestParam String phone, @RequestParam String username, @RequestParam String password) {

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
    public static String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "todolistprofile";
    }


        @GetMapping("/error")
        public String error () {
            return "error";

        }
        @GetMapping("/header")
        public static String header() {
            return "header";

        }
    @GetMapping("/unauthheader")
    public static String unAuthHeader() {
        return "unauthheader";

    }



        @GetMapping("/forgot")
        public static String forgotPage() {
            return "forgot";
        }
        @GetMapping("/task")
        public String task () {
            return "myTask";

        }

@GetMapping("/myTask")
public String myTaskPage(Model model){
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    Users user=usersRepositorie.findByusername(authentication.getName());
        model.addAttribute("todolist",user.getTasks());
        model.addAttribute("del",false);
        //
        List<ToDoListItems> items= ToDoListItemsRepositories.findMytask("0",user.getId());
        model.addAttribute("number",items.size());
        model.addAttribute("todomembers",items);
        //
        return "myTask";
}


        @GetMapping("/profile")
        public  String userProfile(Principal p, Model model) {
        Users newUser=usersRepositorie.findByusername(p.getName());
        newUser.getToDoListItems();
        model.addAttribute("userInfo",newUser);
            return "userProfile";

        }
    @GetMapping("/profile/{id}")
    public  String userProfileById(@PathVariable Long id, Model model) {
        Users newUser=usersRepositorie.finduserById(id);
        model.addAttribute("userInfo",newUser);
        return "userProfile";

    }


///////////////////////////// add to do list/////////////////////////

    @GetMapping("/todolistname") //get
    public static String toDoListName() {
        return "toDoListName";

    }


    @PostMapping ("/addtodo") //post
    public RedirectView addtodo (Principal p,@RequestParam String toDoListName) {

                 Users newUser = usersRepositorie.findByusername(p.getName());
                 ToDoList newList = new ToDoList(toDoListName);
                 newList.setUsers(newUser);
                 ToDoListRepositories.save(newList);
                 newList.getId();
                 return new RedirectView("/listprofile/"+newList.getId()) ;






    }
/////////////////to do list profile الرجاء عدم لمس اي شيء هنا ///////////////////////

    /***
     *
   please don't change anything here
   الرجاء عدم تغيير اي شيء هناااااااااااااااااااااا
     ***/


    @GetMapping ("/listprofile/{id}")
    public String toDoListProfile (Model model , @PathVariable Long id,Principal p) {

        ToDoList toDoList=ToDoListRepositories.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("id",id);
        model.addAttribute("todomembers",toDoList.getMembers());
        model.addAttribute("todolistName", toDoList.getName());
        model.addAttribute("todoitems",ToDoListItemsRepositories.findToDoItems("0",id));



        model.addAttribute("todoadmin",toDoList.getUsers());
        model.addAttribute("posts",postRepositories.findAllPostById(id));

        if (toDoList.getUsers().getId() != usersRepositorie.findByusername(p.getName()).getId())
        {
            model.addAttribute("flag",false);
        }
        else {
            model.addAttribute("flag",true);
            model.addAttribute("delI",true);
        }
        List<ToDoListItems> rejected=ToDoListItemsRepositories.findToDoItems("reject",id);
 List<ToDoListItems> toDoItems=ToDoListItemsRepositories.findToDoItems("0",id);
        List<ToDoListItems> acceptItems=ToDoListItemsRepositories.findToDoItems("accept",id);
        List<ToDoListItems> doneItems=ToDoListItemsRepositories.findToDoItems("done",id);
        model.addAttribute("todoitems",toDoItems);
        model.addAttribute("doingitems",acceptItems);
        model.addAttribute("doneitems",doneItems);
        model.addAttribute("rejecteditems",rejected);
        float total=toDoItems.size()+doneItems.size()+acceptItems.size()+rejected.size();
        float progress=doneItems.size()/total*100;
        model.addAttribute("progress",(int)progress);


        return "todolistprofile";

    }
@PostMapping("/listprofile/addComment/{id}")
public RedirectView addComment ( Principal p,@RequestParam String comment,@PathVariable Long id,Model model) {
    Users newUser=usersRepositorie.findByusername(p.getName());
    ToDoList toDoList=ToDoListRepositories.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    post post=new post();
    post.setComment(comment);
    post.setUsersmember(newUser);
    post.setTodolist(toDoList);
    postRepositories.save(post);
    return new RedirectView("/listprofile/"+id);
}
    @PostMapping ("/listprofile/adduser/{id}") // add user  on to do list
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

        return new RedirectView("/listprofile/"+id+"?adddone=one")  ;}






    @PostMapping ("/listprofile/addtask/{listId}") // add task  on to do list
    public RedirectView addtask (Principal p, Model model,@PathVariable Long listId, @RequestParam String username , @RequestParam String task, @RequestParam String description) {
        Users member = usersRepositorie.findByusername(username);

        ToDoList toDoList = ToDoListRepositories.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + listId));
        Users admin = usersRepositorie.findByusername(p.getName());

        if (toDoList.getMembers().contains(member) || toDoList.getUsers().equals(admin)) {
            ToDoListItems newItem = new ToDoListItems(task, "0", description);

            System.out.println(toDoList.getUsers().getId() + ".....................99");
            System.out.println(admin.getId() + ".....................66");
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


    @GetMapping ("/taskslist")
    public String taskDescription (Principal p, Model model) {

        Users newUser = usersRepositorie.findByusername(p.getName());
        List<ToDoListItems> todolist =  ToDoListItemsRepositories.findByusersmember_id( newUser.getId());
        model.addAttribute("TaskDescriptionList", todolist);

        return "tasksList";
    }


    @GetMapping ("/notifications")
    public String notifications (Principal p,Model model ) {
        Users user = usersRepositorie.findByusername(p.getName());
        List<ToDoListItems> items= ToDoListItemsRepositories.findMytask("0",user.getId());
        model.addAttribute("number",items.size());
        model.addAttribute("todomembers",items);
        return "notfecation";

    }


@PostMapping("taskstatus/{status}/{taskid}")
public RedirectView requistAndAprove (Principal p, @PathVariable String status, @PathVariable Long taskid, @PathParam("todoid") Long todoid)  {
    Users user = usersRepositorie.findByusername(p.getName());
    ToDoListItems task=ToDoListItemsRepositories.findById(taskid)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + taskid));

    if(task.getUsersmember().getId()==user.getId()){

        if(status.equals("accept")){
            task.setStatus("accept");
            ToDoListItemsRepositories.save(task);
                    }

        if(status.equals("reject")){
            task.setStatus("reject");
            ToDoListItemsRepositories.save(task);
               }
        if(status.equals("done")){
            task.setStatus("done");
            ToDoListItemsRepositories.save(task);
            return new RedirectView("/listprofile/"+todoid)  ;
        }
    }


    return new RedirectView("/notifications")  ;
    }
      
         @PostMapping (value = "/dashboard", params = "delete")
    public RedirectView deleteToDo( Long id){
        Optional<ToDoList> toDoDelete=ToDoListRepositories.findById(id);
        ToDoListItemsRepositories.deleteAll(ToDoListItemsRepositories.findBytodolist_id(id));
             postRepositories.deleteAll(postRepositories.findBytodolist_id(id));
        ToDoListRepositories.deleteById(id);

        return new RedirectView("/dashboard");
    }

    @PostMapping("/listprofile/{ToDoId}")
    public RedirectView deleteItem(@PathVariable Long ToDoId,@RequestParam Long id,Principal p){
        ToDoListItemsRepositories.deleteById(id);
        return new RedirectView("/listprofile/"+ToDoId);
    }



}
