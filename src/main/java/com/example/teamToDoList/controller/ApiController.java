package com.example.teamToDoList.controller;

import com.example.teamToDoList.DTO.postDTO;
import com.example.teamToDoList.Repositories.ToDoListItemsRepositories;
import com.example.teamToDoList.Repositories.ToDoListRepositories;
import com.example.teamToDoList.Repositories.UsersRepositorie;
import com.example.teamToDoList.models.ToDoList;
import com.example.teamToDoList.models.ToDoListItems;
import com.example.teamToDoList.models.Users;
import com.example.teamToDoList.models.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000/")
public class ApiController {

    @Autowired
    private ToDoListRepositories toDoListRepositories;
    @Autowired
    UsersRepositorie usersRepositorie;
    @Autowired
    ToDoListItemsRepositories ToDoListItemsRepositories;

    @GetMapping("/lists")
    public List<ToDoList> Lists2() {
        return toDoListRepositories.findAll();
    }


    @GetMapping("/lists/{id}")
    public ToDoList findById(@PathVariable("id") Long id) {
        return toDoListRepositories.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @GetMapping("/profile/{id}")
    public Users userProfileById(@PathVariable Long id) {
        Users newUser = usersRepositorie.finduserById(id);
        return newUser;
    }


    @GetMapping("/myTask")
    public Model myTaskPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersRepositorie.findByusername(authentication.getName());
        model.addAttribute("todolist", user.getTasks());
        model.addAttribute("del", false);
        model.addAttribute("username", user.getUsername());
        //
        List<ToDoListItems> items = ToDoListItemsRepositories.findMytask("0", user.getId());
        model.addAttribute("number", items.size());
        model.addAttribute("todomembers", items);
        //
        return model;
    }

    // To Get All The Task For Current User
    @GetMapping ("/taskslist")
    public List<ToDoListItems> taskDescription (Principal p, Model model) {
        Users newUser = usersRepositorie.findByusername(p.getName());
        List<ToDoListItems> todolist =  ToDoListItemsRepositories.findByusersmember_id( newUser.getId());
        return todolist;
    }

    @PostMapping ("/add-comment/{listId}")
    public HttpStatus addComment (Principal p, @PathVariable("listId")Long id, @RequestBody postDTO postDto) {
        ToDoList todolist =  toDoListRepositories.findById(id).orElseThrow();
        List<post> postes= todolist.getPosts();
        post newPost=new post(postDto.getTime(),postDto.getComment());
        postes.add(newPost);
        todolist.setPosts(postes);
        return HttpStatus.OK;
    }


}
