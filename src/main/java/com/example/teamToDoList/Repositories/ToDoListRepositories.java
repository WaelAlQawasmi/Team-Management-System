package com.example.teamToDoList.Repositories;

import com.example.teamToDoList.models.ToDoList;
import com.example.teamToDoList.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoListRepositories extends JpaRepository<ToDoList,Long> {

}
