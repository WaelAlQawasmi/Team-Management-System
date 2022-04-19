
package com.example.teamToDoList.models;

import lombok.*;


import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "to_do_list_items")
public class ToDoListItems {

    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String status;

    @NonNull
    private String description;

    // Add List Of Items On One To-do List
    @ManyToOne
    ToDoList todolist;

    // Add List Of Items For One User
    @ManyToOne
    Users usersmember;
}

