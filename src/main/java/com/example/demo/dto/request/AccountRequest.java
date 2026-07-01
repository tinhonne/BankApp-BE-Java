package com.example.demo.dto.request;

import com.example.demo.entity.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @NotBlank
    @Pattern(regexp = "\\d{13}")
    private String accountNumber;

    @NotNull
    private Long customerId;

    @NotNull
    @PositiveOrZero
    private Double balance;

}
