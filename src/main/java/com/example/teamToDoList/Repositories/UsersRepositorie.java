package com.example.teamToDoList.Repositories;

import com.example.teamToDoList.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepositorie extends JpaRepository<Users,Long> {

    Users findByusername(String username);
}
