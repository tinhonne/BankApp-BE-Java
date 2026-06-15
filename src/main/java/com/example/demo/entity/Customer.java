package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false,length = 10,unique = true)
    private String identityNo;

    @Column()
    private String mobile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createDatetime;

    @Column(nullable = false)
    private LocalDateTime updateDatetime;

}
