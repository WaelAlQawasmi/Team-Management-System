package com.example.teamToDoList.models;


import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import java.util.Collection;

import java.util.List;


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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
