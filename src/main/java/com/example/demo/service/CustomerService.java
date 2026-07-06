package com.example.demo.service;

import com.example.demo.dto.request.CustomerCreateRequest;
import com.example.demo.dto.request.CustomerSearchRequest;
import com.example.demo.dto.request.CustomerUpdateRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.dto.response.PageResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerCreateRequest request);
//    List<CustomerResponse> getCustomer();
    CustomerResponse getCustomerById(Long id);
    CustomerResponse updateCustomerById(Long id, CustomerUpdateRequest request);
    void deleteCustomerById(Long id);
    PageResponse<CustomerResponse> getAllCustomerSortByName(int page, int size);
    PageResponse<CustomerResponse> getCustomerSortByField(CustomerSearchRequest request, int page, int size);
}
