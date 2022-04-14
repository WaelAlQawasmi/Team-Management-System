package com.example.teamToDoList.secuirty;

import com.example.teamToDoList.Repositories.UsersRepositorie;
import com.example.teamToDoList.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service // to create service
public class userDetalesServiseimp implements UserDetailsService { // this interface use to know the login page whatch reo use
    @Autowired
    UsersRepositorie UserRebo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {// you must added
        System.out.println(username);
        return UserRebo.findByusername(username);
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        Users customer = UserRebo.findByEmail(email);
        if (customer != null) {
            customer.setResetPasswordToken(token);
            UserRebo.save(customer);
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public Users getByResetPasswordToken(String token) {
        return UserRebo.findByResetPasswordToken(token);
    }

    public void updatePassword(Users customer, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);

        customer.setResetPasswordToken(null);
        UserRebo.save(customer);
    }
}
