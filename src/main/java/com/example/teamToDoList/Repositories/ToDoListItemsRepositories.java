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
List<ToDoListItems> findByusersmember_id (Long id);


    @Query("SELECT c FROM ToDoListItems c WHERE c.status = ?1 and c.usersmember.id = ?2")
   List<ToDoListItems>  findMytask(String ststes ,Long userId);


    @Query("SELECT c FROM ToDoListItems c WHERE c.status = ?1 and c.todolist.id = ?2")
    List<ToDoListItems>  findToDoItems(String ststes ,Long todolistid);

}
