package com.example.teamToDoList.models;

import lombok.*;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class todolist  {
    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue
    private long id;
    @NonNull
    private String name;



}
