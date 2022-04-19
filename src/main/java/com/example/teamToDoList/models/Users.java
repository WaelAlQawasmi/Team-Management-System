
package com.example.teamToDoList.models;


import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;

import java.util.Collection;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "users")
@RequiredArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails {

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    @Column (unique = true)
    @NonNull
    private String email;
    @NonNull
    private String birthDay;
    @NonNull
    private String position;
    @NonNull
    private String address;
    @NonNull
    private String phone;
    @Column(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;

    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    @OneToMany(mappedBy="users")
    List<ToDoList> todolists ;

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    public List<ToDoList> tasks;

    @OneToMany(mappedBy="usersmember")
    List<ToDoListItems> toDoListItems ;

    @OneToMany(mappedBy="usersmember")
    List <post>posts;
}

