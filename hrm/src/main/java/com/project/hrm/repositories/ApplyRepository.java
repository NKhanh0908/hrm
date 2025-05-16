package com.project.hrm.repositories;

import com.project.hrm.entities.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Integer>, JpaSpecificationExecutor<Apply> {
}
