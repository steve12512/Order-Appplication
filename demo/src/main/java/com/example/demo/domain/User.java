package com.example.demo.domain;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "users")
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
  String role = "User";

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  public User() {}

  public boolean getIsActive() {
    return isActive;
  }

  public User(String username, String email, int age, boolean isActive) {
    this.username = username;
    this.email = email;
    this.age = age;
    this.isActive = isActive;
  }
}
