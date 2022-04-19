
package com.example.teamToDoList.models;

import lombok.*;


import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class ToDoList {

    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue
    private long id;
    @NonNull
    private String name;

    // To Determine The Admin Of To-do List
    @ManyToOne
    Users users;

    // Members Of To-do List
    @ManyToMany
        @JoinTable(
            name = "todolistmembers",
            joinColumns = {@JoinColumn(name = "todo_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
        )
    List<Users> members;

    // Add List Of Comment
    @OneToMany(mappedBy = "todolist")
    List<post>posts;

    // Add List Of Items
    @OneToMany(mappedBy = "todolist")
    List<ToDoListItems>toDoListItems;


}

