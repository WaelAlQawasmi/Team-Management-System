package com.example.teamToDoList.Repositories;

import com.example.teamToDoList.models.ToDoList;
import com.example.teamToDoList.models.Users;
import com.example.teamToDoList.models.apiLists;
import com.example.teamToDoList.models.post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepositories extends JpaRepository<ToDoList,Long> {

//    @Query("SELECT " +
//            "    new com.example.teamToDoList.models.apiLists(u.name) " +
//            " FROM " +
//            " ToDoList u ")
    @Query("SELECT u.name FROM ToDoList u  ")
   // public List<apiLists> getYearWisePostCount();
//    List<CommentCount> countTotalCommentsByYearClass();
//
//    @Query("SELECT u.name AS name,u.users.firstName AS firstName FROM  ToDoList u")

    List<Object[]> findAllNname();
}
