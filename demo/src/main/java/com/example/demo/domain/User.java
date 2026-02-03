package com.example.demo.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table (name = "users")
public class User implements UserDetails {
    String username;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    int age;
    @Column(nullable = false)
    boolean isActive = true;
    String password;
    String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled(){return  true;}

    @Override
    public boolean isCredentialsNonExpired(){return  true;}

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }


    @Override
    public boolean isAccountNonLocked(){
        return  true;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    protected  User(){}

    public User(String username, String email, int age, boolean isActive) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.isActive = isActive;
    }
}