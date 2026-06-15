package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateRequest {

    private String name;
    private LocalDate birthday;
    private String address;
    private String identityNo;
    private String mobile;
    private String customerType;
    private Integer status;
}
