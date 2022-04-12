package com.example.teamToDoList.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface todolistRepositories extends JpaRepository<todolist,Long> {
}
