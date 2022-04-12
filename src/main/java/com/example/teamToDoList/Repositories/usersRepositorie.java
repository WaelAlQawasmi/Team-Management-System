package com.example.teamToDoList.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usersRepositorie extends JpaRepository<users,Long> {

    users findByusername(String username);
}
