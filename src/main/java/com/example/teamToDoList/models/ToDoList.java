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

@ManyToOne
    Users users;

@OneToMany(mappedBy = "todolist")
//@JoinTable(
//        name = "todolistmembers",
//        joinColumns = {@JoinColumn(name = "todo_id")},
//        inverseJoinColumns = {@JoinColumn(name = "user_id")}
//)
List<Users> members;


}
