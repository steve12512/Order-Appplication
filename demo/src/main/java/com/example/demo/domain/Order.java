package com.example.demo.domain;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String status;

    @Timestamp
    @Column(updatable = false)
    private Timestamp created_at;


}