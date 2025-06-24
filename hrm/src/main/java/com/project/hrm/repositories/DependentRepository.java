package com.project.hrm.repositories;

import com.project.hrm.entities.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependentRepository extends JpaRepository<Dependent, Integer>, JpaSpecificationExecutor<Dependent> {
    @Query(value = "SELECT * FROM dependent WHERE employee_id = :employeeId", nativeQuery = true)
    List<Dependent> findByEmployeeIdNative(@Param("employeeId") Integer employeeId);
}
