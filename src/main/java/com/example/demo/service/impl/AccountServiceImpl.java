package com.example.demo.service.impl;

import com.example.demo.dto.request.AccountRequest;
import com.example.demo.dto.response.AccountResponse;
import com.example.demo.dto.response.PageResponse;
import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.AccountMapping;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountMapping accountMapping;

// create new account by customerId

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {
        Customer customer=customerRepository.findById(accountRequest.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        if(accountRepository.existsByAccountNumber(accountRequest.getAccountNumber())){
            throw new AppException(ErrorCode.ACCOUNT_NUMBER_EXISTED);
        }
        Account account=accountMapping.toEntity(accountRequest);
        account.setCustomer(customer);
        account.setStatus(3);
        account.setBalance(account.getBalance() !=null ? account.getBalance():0.0);
        accountRepository.save(account);
        return accountMapping.toResponse(account);
    }

//Seach account by id
    @Override
    public AccountResponse getAccountById(Long id){
        Account account=accountRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        return accountMapping.toResponse(account);

    }
// Seach account by accountnumber
    @Override
    public AccountResponse getAccountByAccountNumber(String accountNumber){
        Account account=accountRepository.findByAccountNumber(accountNumber);
        if(account==null){
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return accountMapping.toResponse(account);

    }
//Search all account and sort by customer.name
    @Override
    public PageResponse<AccountResponse> getAccountSortByNameCustomer(int page,int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Account> accounts=accountRepository.findAllSortedByCustomerName(pageable);
        return PageResponse.from(accounts.map(accountMapping::toResponse));
    }

// Search active account by customer.id and sort accountNumber

    @Override
    public PageResponse<AccountResponse> getActiveAccountByCustomerId(Long id,int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        Page<Account> accounts=accountRepository.findActiveAccountByCustomerId(id,pageable);
        return PageResponse.from(accounts.map(accountMapping::toResponse));
    }
}
