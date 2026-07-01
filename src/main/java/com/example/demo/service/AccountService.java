package com.example.demo.service;

import com.example.demo.dto.request.AccountRequest;
import com.example.demo.dto.response.AccountResponse;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.dto.response.PageResponse;


public interface AccountService {
    AccountResponse createAccount(AccountRequest accountRequest);
    AccountResponse getAccountById(Long id);
    AccountResponse getAccountByAccountNumber(String accountNumber);
}
