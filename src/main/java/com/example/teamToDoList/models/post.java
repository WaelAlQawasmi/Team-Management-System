package com.example.teamToDoList.models;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "post")
public class post {

    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String time;
    @NonNull
    private String comment;

    // To Determine The To-do List Of Post
    @ManyToOne
    ToDoList todolist;

    // To Determine The Owner Of Post
    @ManyToOne
    Users usersmember;
}