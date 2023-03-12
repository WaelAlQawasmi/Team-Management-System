package com.example.teamToDoList.controller;

import com.example.teamToDoList.Repositories.ToDoListRepositories;
import com.example.teamToDoList.models.ToDoList;
import com.example.teamToDoList.models.apiLists;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.awt.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000/")
public class ApiController {
    @Autowired
    private ToDoListRepositories toDoListRepositories;




    @GetMapping("/lists")
    public List<ToDoList> Lists2() throws IOException {
        return toDoListRepositories.findAll();
    }


    @GetMapping("/lists/{id}")
    public ToDoList findById(@PathVariable("id")Long id){
        return toDoListRepositories.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }


}
