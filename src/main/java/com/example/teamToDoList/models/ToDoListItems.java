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


    @ManyToOne
    ToDoList todolist;

    @ManyToOne
    Users usersmember;
}
