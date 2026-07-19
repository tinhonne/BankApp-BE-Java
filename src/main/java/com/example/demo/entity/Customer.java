package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
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

}
