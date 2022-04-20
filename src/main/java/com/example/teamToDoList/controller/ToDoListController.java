package com.example.teamToDoList.controller;

import com.example.teamToDoList.Repositories.PostRepostries;
import com.example.teamToDoList.Repositories.UsersRepositorie;
import com.example.teamToDoList.models.ToDoList;
import com.example.teamToDoList.models.ToDoListItems;
import com.example.teamToDoList.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class ToDoListController {

    @Autowired
    PasswordEncoder passwordEncoder;

    private com.example.teamToDoList.Repositories.ToDoListItemsRepositories ToDoListItemsRepositories;
    private UsersRepositorie usersRepositorie ;
    private com.example.teamToDoList.Repositories.ToDoListRepositories ToDoListRepositories;
    private final PostRepostries postRepositories;
    public ToDoListController(UsersRepositorie usersRepositorie, com.example.teamToDoList.Repositories.ToDoListRepositories toDoListRepositories, com.example.teamToDoList.Repositories.ToDoListRepositories toDoListRepositoriesl, com.example.teamToDoList.Repositories.ToDoListItemsRepositories toDoListItemsRepositories, PostRepostries postRepositories) {
        this.usersRepositorie = usersRepositorie;
        ToDoListRepositories = toDoListRepositories;
        ToDoListItemsRepositories = toDoListItemsRepositories;
        this.postRepositories = postRepositories;
    }

    // To Get Create Template
    @GetMapping("/todolistname") //get
    public static String toDoListName() {
        return "toDoListName";

    }

    // Create New To-do List
    @PostMapping("/addtodo")
    public RedirectView addToDo(Principal p, @RequestParam String toDoListName) {

        Users newUser = usersRepositorie.findByusername(p.getName());
        ToDoList newList = new ToDoList(toDoListName);
        newList.setUsers(newUser);
        ToDoListRepositories.save(newList);
        newList.getId();

        return new RedirectView("/listprofile/"+newList.getId()) ;
    }

    // Profile Of To-do List
    @GetMapping ("/listprofile/{id}")
    public String toDoListProfile (Model model , @PathVariable Long id, Principal p) {

        // 1. Find The To-do List Object
        ToDoList toDoList=ToDoListRepositories.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // 2. Store Some Data Inside model
        model.addAttribute("id",id);
        model.addAttribute("todomembers",toDoList.getMembers());
        model.addAttribute("todolistName", toDoList.getName());
        model.addAttribute("currentuser",usersRepositorie.findByusername(p.getName()).getId());
        model.addAttribute("todoadmin",toDoList.getUsers());
        model.addAttribute("posts",postRepositories.findAllPostById(id));
// get time now
        LocalDateTime myDateObj = LocalDateTime.now();

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = myDateObj.format(myFormatObj);
        model.addAttribute("timeNow",formattedDate);
     ///


        // 3. If The Current User is not The Admin Of The To-do List
        if (toDoList.getUsers().getId() != usersRepositorie.findByusername(p.getName()).getId())//
        {
            model.addAttribute("flag",false);
        }
        else {
            model.addAttribute("flag",true);
            model.addAttribute("delI",true);
        }

        // 4. Follow The Tasks Of To-do List & to Calculate progress  Bar
        List<ToDoListItems> rejected=ToDoListItemsRepositories.findToDoItems("reject",id);
        List<ToDoListItems> toDoItems=ToDoListItemsRepositories.findToDoItems("0",id);
        List<ToDoListItems> acceptItems=ToDoListItemsRepositories.findToDoItems("accept",id);
        List<ToDoListItems> doneItems=ToDoListItemsRepositories.findToDoItems("done",id);


        model.addAttribute("todoitems",toDoItems);
        model.addAttribute("doingitems",acceptItems);
        model.addAttribute("doneitems",doneItems);
        model.addAttribute("rejecteditems",rejected);

        // 5. Calculation The ProgressBar
        float total=toDoItems.size()+doneItems.size()+acceptItems.size()+rejected.size();
        float progress=doneItems.size()/total*100;

        model.addAttribute("progress",(int)progress);


        return "todolistprofile";

    }

    // To Change Status Of Specific To-do List Items
    @PostMapping("taskstatus/{status}/{taskid}")
    public RedirectView requistAndAprove (Principal p, @PathVariable String status, @PathVariable Long taskid, @PathParam("todoid") Long todoid)  {
        // 1. To Get Current User
        Users user = usersRepositorie.findByusername(p.getName());

        // 2. To Get The Task
        ToDoListItems task=ToDoListItemsRepositories.findById(taskid).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + taskid));

        // 3. To Check If The Current User is The Owner Of The Task
        if(task.getUsersmember().getId()==user.getId()){

            // 4. To Accept The Task
            if(status.equals("accept")){
                task.setStatus("accept");
                ToDoListItemsRepositories.save(task);
            }

            // 5. To Reject The Task
            if(status.equals("reject")){
                task.setStatus("reject");
                ToDoListItemsRepositories.save(task);
            }

            // 6. To Complete The Task
            if(status.equals("done")){
                task.setStatus("done");
                ToDoListItemsRepositories.save(task);
                return new RedirectView("/listprofile/"+todoid)  ;
            }
        }
        return new RedirectView("/notifications")  ;
    }

    // Delete To-do List From The /dashboard route
    @PostMapping (value = "/dashboard", params = "delete")
    public RedirectView deleteToDo( Long id){
        Optional<ToDoList> toDoDelete=ToDoListRepositories.findById(id);
        ToDoListItemsRepositories.deleteAll(ToDoListItemsRepositories.findBytodolist_id(id));
        postRepositories.deleteAll(postRepositories.findBytodolist_id(id));
        ToDoListRepositories.deleteById(id);

        return new RedirectView("/dashboard");
    }

    // To Delete The Item From To-do List
    @PostMapping("/listprofile/{ToDoId}")
    public RedirectView deleteItem( @PathVariable Long ToDoId,@RequestParam Long id ){
        ToDoListItemsRepositories.deleteById(id);
        return new RedirectView("/listprofile/"+ToDoId);
    }
}
