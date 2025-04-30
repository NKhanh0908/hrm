package com.project.hrm.repositories;

import com.project.hrm.entities.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Integer> {
}
