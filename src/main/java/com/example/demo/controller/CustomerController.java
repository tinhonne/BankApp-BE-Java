package com.example.demo.controller;

import com.example.demo.dto.request.CustomerCreateRequest;
import com.example.demo.dto.request.CustomerSearchRequest;
import com.example.demo.dto.request.CustomerUpdateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.dto.response.PageResponse;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")

public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid  @RequestBody CustomerCreateRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(customerService.createCustomer(request)));
    }

//    @GetMapping
//    public ApiResponse<List<CustomerResponse>> getCustomer(){
//        return ApiResponse.success(customerService.getCustomer());
//    }
    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable Long id){
        return ApiResponse.success(customerService.getCustomerById(id));
    }
    @PutMapping("/{id}")
    public  ApiResponse<CustomerResponse> updateCustomerById(@PathVariable Long id,@Valid @RequestBody CustomerUpdateRequest request){
        return ApiResponse.success(customerService.updateCustomerById(id,request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomerById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ApiResponse<PageResponse<CustomerResponse>> getAllCustomerSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(customerService.getAllCustomerSortByName(page, size));
    }
    @GetMapping("/by-field")
    public  ApiResponse<PageResponse<CustomerResponse>> getCustomerSortByField(
            @ModelAttribute CustomerSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        return ApiResponse.success(customerService.getCustomerSortByField(request,page,size));
    }
}
