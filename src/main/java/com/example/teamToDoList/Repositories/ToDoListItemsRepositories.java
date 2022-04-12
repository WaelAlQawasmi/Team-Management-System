package com.example.teamToDoList.Repositories;


import com.example.teamToDoList.models.ToDoListItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ToDoListItemsRepositories extends JpaRepository<ToDoListItems,Long> {

}
