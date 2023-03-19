package com.example.teamToDoList.DTO;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class postDTO {


    private Long id;

    private String time;

    private String comment;
}
