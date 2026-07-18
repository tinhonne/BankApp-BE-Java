package com.example.demo.service.impl;

import com.example.demo.dto.request.CustomerCreateRequest;
import com.example.demo.dto.request.CustomerSearchRequest;
import com.example.demo.dto.request.CustomerUpdateRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.dto.response.PageResponse;
import com.example.demo.entity.Customer;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.CustomerMapping;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CustomerMapping customerMapping;

    // create new customer
    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest request){
        if(customerRepository.existsByIdentityNo(request.getIdentityNo()))
            throw new AppException(ErrorCode.CUSTOMER_EXISTED);

        Customer customer= customerMapping.toEntity(request);
        customerRepository.save(customer);

        return customerMapping.toResponse(customer);
    }


    //Search customer by id
    @Override
    public CustomerResponse getCustomerById(Long id){
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        return customerMapping.toResponse(customer);
    }

    //update customer by id
    @Override
    public CustomerResponse updateCustomerById(Long id, CustomerUpdateRequest request){
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        customerMapping.toUpdateCustomerByID(customer,request);
        customerRepository.save(customer);
        return customerMapping.toResponse(customer);

    }

    // delete customer by id
    @Override
    public void deleteCustomerById(Long id){
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        if(accountRepository.existsByCustomerIdAndStatus(id,1)){
            throw new AppException(ErrorCode.CUSTOMER_HAS_ACCOUNT);
        }
        customer.setStatus(0);
        customerRepository.save(customer);
    }

    //Search customer sort by name use Pageable
    @Override
    public PageResponse<CustomerResponse> getAllCustomerSortByName(int page, int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Customer> customers=customerRepository.findAllSortedByName(pageable);
        return PageResponse.from(customers.map(customerMapping::toResponse));
    }

    //Search customer sort by field use Pageable
    @Override
    public PageResponse<CustomerResponse> getCustomerSortByField(CustomerSearchRequest request, int page, int size){
        Pageable pageable=PageRequest.of(page,size,Sort.by("name").ascending());
        Page<Customer> customers=customerRepository.findAll(CustomerSpecification.filter(request),pageable);
        return PageResponse.from(customers.map(customerMapping::toResponse));
    }


}
