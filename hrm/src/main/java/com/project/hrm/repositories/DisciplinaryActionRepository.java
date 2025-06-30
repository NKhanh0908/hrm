package com.project.hrm.repositories;

import com.project.hrm.entities.DisciplinaryAction;
import com.project.hrm.entities.Employees;
import org.hibernate.Internal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DisciplinaryActionRepository extends JpaRepository<DisciplinaryAction, Integer>, JpaSpecificationExecutor<DisciplinaryAction> {
    List<DisciplinaryAction> findAllByEmployeeIdAndDateBetween(Integer employeeId, LocalDateTime start, LocalDateTime end);
}
