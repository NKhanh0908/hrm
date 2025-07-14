package com.project.hrm.auth.repository;

import com.project.hrm.auth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    @Query(value = """
            select a.username
                from account a inner join employees e
                    on a.employees_id = e.id
                where e.id = :employeeId""", nativeQuery = true)
    String getUserNameByEmployeeId(@Param("employeeId") Integer employeeId);
}
