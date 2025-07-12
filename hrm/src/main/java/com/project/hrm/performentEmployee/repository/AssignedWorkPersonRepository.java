package com.project.hrm.performentEmployee.repository;

import com.project.hrm.performentEmployee.entity.AssignedWorkPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignedWorkPersonRepository extends JpaRepository<AssignedWorkPerson, Integer> {
    List<AssignedWorkPerson> findAllByEmployeeId(Integer employeeId);

    Page<AssignedWorkPerson> findAllByEmployeeId(Integer employeeId, Pageable pageable);
}
