package com.example.demo.controller;

import com.example.demo.dto.request.CustomerCreateRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")

public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public CustomerResponse createCustomer(@RequestBody CustomerCreateRequest request){
        return customerService.createCustomer(request);
    }
    @GetMapping
    public List<CustomerResponse> getCustomer(){
        return customerService.getCustomer();
    }
    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }
    @PutMapping("/{id}")
    public  CustomerResponse updateCustomerById(@PathVariable Long id,@RequestBody CustomerCreateRequest request){
        return customerService.updateCustomerById(id,request);
    }
    @DeleteMapping("/{id}")
    public String deleteCustomerById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
        return "Xoa khach hang thanh cong";
    }

}
