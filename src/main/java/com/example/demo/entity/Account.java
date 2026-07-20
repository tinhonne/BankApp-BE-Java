package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(length = 13,nullable = false,unique = true)
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name="customer_id",nullable = false)
    private Customer customer;

    @Column(nullable = false, precision = 19, scale = 2)
    @PositiveOrZero
    private BigDecimal balance;

    @Column(nullable = false)
    private Integer status;


}
