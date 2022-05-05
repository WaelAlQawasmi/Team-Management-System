
package com.example.teamToDoList.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity


public class ToDoList  {

    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue
    private long id;
    @NonNull
    private String name;


    @JsonProperty("owner")
    @JsonIgnoreProperties({ "lastName", "birthDay" ,"password", "resetPasswordToken","enabled" ,
            "accountNonExpired",
            "credentialsNonExpired",
            "authorities" ,
            "accountNonLocked"})
    // To Determine The Admin Of To-do List
    @ManyToOne
    Users users;
    @JsonIgnore
    // Members Of To-do List
    @ManyToMany
        @JoinTable(
            name = "todolistmembers",
            joinColumns = {@JoinColumn(name = "todo_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
        )
    List<Users> members;
    @JsonIgnore
    // Add List Of Comment
    @OneToMany(mappedBy = "todolist")
    List<post>posts;
    @JsonIgnore
    // Add List Of Items
    @OneToMany(mappedBy = "todolist")
    List<ToDoListItems>toDoListItems;


}

