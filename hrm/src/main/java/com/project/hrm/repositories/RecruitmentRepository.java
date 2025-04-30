package com.project.hrm.repositories;

import com.project.hrm.entities.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
}
