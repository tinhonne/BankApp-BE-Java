package com.example.demo.mapper;

import com.example.demo.dto.request.AccountRequest;
import com.example.demo.dto.response.AccountResponse;
import com.example.demo.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapping  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "status", ignore = true)
    Account toEntity(AccountRequest accountRequest);

    @Mapping(source ="customer.id" , target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    AccountResponse toResponse(Account account);
}
