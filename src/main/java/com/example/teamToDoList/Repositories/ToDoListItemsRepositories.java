package com.example.teamToDoList.Repositories;


import com.example.teamToDoList.models.ToDoListItems;
import com.example.teamToDoList.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ToDoListItemsRepositories extends JpaRepository<ToDoListItems,Long> {
List<ToDoListItems>findBytodolist_id(Long id);






}
