package com.example.demo.specification;

import com.example.demo.dto.request.CustomerSearchRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerType;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    private CustomerSpecification(){}

    private static Specification<Customer> like(String field,String value){
        return(root,query,cb) ->{
            if(value==null || value.isBlank()){
                return cb.conjunction();
            }
            return cb.like(
                    cb.lower(root.get(field)),"%"+value.toLowerCase()+"%"
            );
        };
    }
    public static Specification<Customer> hasName(String name){
        return like("name",name);
    }
    public  static Specification<Customer> hasIdentityNo(String identityNo){
        return like("identityNo",identityNo);
    }
    public static Specification<Customer> hasMobile(String mobile){
        return like("mobile",mobile);
    }

    private static Specification<Customer> hasStatus(Integer status){
        return(root, query, cb) -> {
            if(status == null){
                return cb.conjunction();
            }
            return cb.equal(root.get("status"),status);
        };
    }
    private static Specification<Customer> hasCustomerType(CustomerType customerType){
        return(root,query,cb) ->{
            if(customerType==null){
                return cb.conjunction();
            }
            return cb.equal(root.get("customerType"),customerType);
        };

    }
    public static Specification<Customer> filter(CustomerSearchRequest request){
        return Specification.where(hasName(request.getName()))
                .and(hasIdentityNo(request.getIdentityNo()))
                .and(hasMobile(request.getMobile()))
                .and(hasStatus(request.getStatus()))
                .and(hasCustomerType(request.getCustomerType()));
    }
}
