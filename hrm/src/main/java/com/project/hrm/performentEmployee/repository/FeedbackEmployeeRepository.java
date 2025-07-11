package com.project.hrm.performentEmployee.repository;

import com.project.hrm.performentEmployee.entity.FeedbackEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackEmployeeRepository extends JpaRepository<FeedbackEmployee, Integer>, JpaSpecificationExecutor<FeedbackEmployee> {
}
