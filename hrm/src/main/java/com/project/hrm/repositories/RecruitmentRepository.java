package com.project.hrm.repositories;

import com.project.hrm.entities.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
    Page<Recruitment> filter(Specification<Recruitment> recruitmentSpecification, Pageable pageable);
}
