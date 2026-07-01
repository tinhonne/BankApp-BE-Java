package com.example.demo.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccountResponse {

    private Long id;
    private Long customerId;
    private String customerName;
    private String accountNumber;
    private Double balance;
    private Integer status;

    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
}
