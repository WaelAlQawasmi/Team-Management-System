package com.example.teamToDoList.Repositories;

import com.example.teamToDoList.models.ToDoListItems;
import com.example.teamToDoList.models.Users;
import com.example.teamToDoList.models.post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepostries  extends JpaRepository<post,Long> {
     @Query("SELECT u FROM post u WHERE u.todolist.id = ?1")
    List<post> findAllPostById(Long id);
    List<post> findBytodolist_id(Long id);

}
