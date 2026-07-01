package com.example.demo.dto.request;

import com.example.demo.entity.CustomerType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerSearchRequest {
    private String name;
    private String identityNo;
    private String mobile;
    private CustomerType customerType;
    private Integer status;
}
