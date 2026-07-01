package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    boolean existsByIdentityNo(String identityNo);

//    @Query("Select c From Customer c Order by c.name ASC")
//    Page<Customer> findAllSortedByName(Pageable pageable);

    Page<Customer> findAll(Specification<Customer> spec, Pageable pageable);


}
