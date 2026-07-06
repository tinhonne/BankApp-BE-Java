package com.example.demo.repository;

import com.example.demo.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account,Long> {
    boolean existsByAccountNumber(String accountNumber);
    Account findByAccountNumber(String accountNumber);
    boolean existsByCustomerIdAndStatus(Long id, Integer status);
    @Query("select a from Account a join a.customer c order by c.name ASC ")
    Page<Account> findAllSortedByCustomerName(Pageable pageable);

    @Query("""
    Select a From Account a
            Where a.customer.id=?1
            And a.status=1
            Order by a.accountNumber ASC
""")
    Page<Account> findActiveAccountByCustomerId(Long id, Pageable pageable);
}