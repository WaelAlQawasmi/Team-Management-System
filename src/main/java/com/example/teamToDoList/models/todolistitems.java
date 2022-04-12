package com.example.teamToDoList.models;

import lombok.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity

public class todolistitems  {
    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
}
