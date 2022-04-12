package com.example.teamToDoList.models;


import lombok.*;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity


public class users implements UserDetails {
    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @Column(unique = true)
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String FirstName;
    @NonNull
    private String LastName;
    @NonNull
    private String email;
    @NonNull
    private String birthDay;
    @NonNull
    private String phone;


    @Override
    public GrantedAuthority[] getAuthorities() {
        return new GrantedAuthority[0];
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
