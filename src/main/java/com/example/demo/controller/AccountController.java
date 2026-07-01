package com.example.demo.controller;


import com.example.demo.dto.request.AccountRequest;
import com.example.demo.dto.response.AccountResponse;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ApiResponse<AccountResponse> createAccount(@Valid @RequestBody AccountRequest accountRequest){
        return ApiResponse.success(accountService.createAccount(accountRequest));
    }
    @GetMapping("/{id}")
    public ApiResponse<AccountResponse> getAccountById(@PathVariable Long id){
        return ApiResponse.success(accountService.getAccountById(id));
    }
    @GetMapping("/by_number/{accountNumber}")
    public ApiResponse<AccountResponse> getAccountByAccountNumber(@PathVariable String accountNumber){
        return ApiResponse.success(accountService.getAccountByAccountNumber(accountNumber));

    }

}
