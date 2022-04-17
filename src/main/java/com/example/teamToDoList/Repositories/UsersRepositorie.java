package com.example.teamToDoList.Repositories;

import com.example.teamToDoList.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepositorie extends JpaRepository<Users,Long> {

    Users findByusername(String username);
    @Query("SELECT c FROM Users c WHERE c.id = ?1")
    public Users finduserById(Long id);
    public Users findByResetPasswordToken(String token);
    @Query("SELECT c FROM Users c WHERE c.email = ?1")
    public Users findByEmail(String email);


}
