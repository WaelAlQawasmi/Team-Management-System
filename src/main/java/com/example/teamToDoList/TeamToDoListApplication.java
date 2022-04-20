package com.example.teamToDoList;

import com.example.teamToDoList.controller.TimeDifferenceClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeamToDoListApplication {

	public static void main(String[] args) {
		//System.out.println(TimeDifferenceClass.findDifference("10-01-2018 01:10:20","10-01-2019 02:10:20"));
		SpringApplication.run(TeamToDoListApplication.class, args);
	}

}
