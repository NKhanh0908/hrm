package com.project.hrm.repositories;

import com.project.hrm.entities.FeedbackEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackEmployeeRepository extends JpaRepository<FeedbackEmployee, Integer>, JpaSpecificationExecutor<FeedbackEmployee> {
}
