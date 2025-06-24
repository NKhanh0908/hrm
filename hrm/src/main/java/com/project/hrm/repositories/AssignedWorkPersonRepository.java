package com.project.hrm.repositories;

import com.project.hrm.entities.AssignedWorkPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignedWorkPersonRepository extends JpaRepository<AssignedWorkPerson, Integer> {
    List<AssignedWorkPerson> findAllByEmployeeId(Integer employeeId);
}
