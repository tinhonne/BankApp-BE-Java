package com.example.demo.controller;


import com.example.demo.dto.request.AccountRequest;
import com.example.demo.dto.response.AccountResponse;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.PageResponse;
import com.example.demo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountRequest accountRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                        .body(ApiResponse.success(accountService.createAccount(accountRequest)));
    }
    @GetMapping("/{id}")
    public ApiResponse<AccountResponse> getAccountById(@PathVariable Long id){
        return ApiResponse.success(accountService.getAccountById(id));
    }
    @GetMapping("/by-number/{accountNumber}")
    public ApiResponse<AccountResponse> getAccountByAccountNumber(@PathVariable String accountNumber){
        return ApiResponse.success(accountService.getAccountByAccountNumber(accountNumber));

    }
    @GetMapping
    public ApiResponse<PageResponse<AccountResponse>> getAccountSortByNameCustomer(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size
    ){
        return ApiResponse.success(accountService.getAccountSortByNameCustomer(page,size));
    }

    @GetMapping("/{id}/active")
    public  ApiResponse<PageResponse<AccountResponse>> getActiveAccountByCustomerId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size
    ){
      return ApiResponse.success(accountService.getActiveAccountByCustomerId(id,page,size));
    };
}
