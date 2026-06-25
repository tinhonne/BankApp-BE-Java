package com.example.demo.controller;

import com.example.demo.dto.request.CustomerCreateRequest;
import com.example.demo.dto.request.CustomerUpdateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")

public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(@Valid  @RequestBody CustomerCreateRequest request){
        return ApiResponse.success(customerService.createCustomer(request));
    }
    @GetMapping
    public ApiResponse<List<CustomerResponse>> getCustomer(){
        return ApiResponse.success(customerService.getCustomer());
    }
    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable Long id){
        return ApiResponse.success(customerService.getCustomerById(id));
    }
    @PutMapping("/{id}")
    public  ApiResponse<CustomerResponse> updateCustomerById(@PathVariable Long id,@Valid @RequestBody CustomerUpdateRequest request){
        return ApiResponse.success(customerService.updateCustomerById(id,request));
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCustomerById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
        return ApiResponse.success("Xoa khach hang thanh cong");
    }

}
