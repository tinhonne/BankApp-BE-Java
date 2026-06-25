package com.example.demo.service.impl;

import com.example.demo.dto.request.CustomerCreateRequest;
import com.example.demo.dto.request.CustomerUpdateRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.entity.Customer;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.CustomerMapping;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapping customerMapping;

    // create customer new
    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest request){
        if(customerRepository.existsByIdentityNo(request.getIdentityNo()))
            throw new AppException(ErrorCode.CUSTOMER_EXISTED);

        Customer customer= customerMapping.toEntity(request);
        customer.setCreateDatetime(LocalDateTime.now());
        customer.setUpdateDatetime(LocalDateTime.now());
        customerRepository.save(customer);

        return customerMapping.toResponse(customer);
    }

    // seach all customer
    @Override
    public List<CustomerResponse> getCustomer(){
        List<Customer> customers =customerRepository.findAll();
        List<CustomerResponse> responses=new ArrayList<>();
        for(Customer customer: customers){
            responses.add(customerMapping.toResponse(customer));
        }
        return responses;
    }

    //seach customer by id
    @Override
    public CustomerResponse getCustomerById(Long id){
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        return customerMapping.toResponse(customer);
    }

    //update customer
    @Override
    public CustomerResponse updateCustomerById(Long id, CustomerUpdateRequest request){
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        customerMapping.toUpdateCustomerByID(customer,request);
        customer.setUpdateDatetime(LocalDateTime.now());
        customerRepository.save(customer);
        return customerMapping.toResponse(customer);

    }

    // delete customer by id
    @Override
    public void deleteCustomerById(Long id){
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        customerRepository.delete(customer);
    }
}
