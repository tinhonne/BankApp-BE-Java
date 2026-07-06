package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 15,nullable = false,unique = true)
    String username;

    @Column(length = 100,nullable = false)
    String password;

    @Column(length = 20,nullable = false)
    String name;

    @Column(nullable = false)
    Integer role;


}
