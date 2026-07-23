package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;

    @Column(length = 15,nullable = false,unique = true)
    String username;

    @Column(length = 100,nullable = false)
    String password;

    @Column(length = 20,nullable = false)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    RoleType role;

}
