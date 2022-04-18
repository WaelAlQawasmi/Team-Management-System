package com.example.teamToDoList.models;

import lombok.*;

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
    private String comment;

    @ManyToOne
    ToDoList todolist;

    @ManyToOne
    Users usersmember;
}