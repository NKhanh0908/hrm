package com.project.hrm.repositories;

import com.project.hrm.entities.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Integer>, JpaSpecificationExecutor<CandidateProfile> {
    @Query(value = """
            SELECT c.* FROM (
                SELECT candidate_profile_id
                FROM apply
                WHERE id = :applyId
            ) a INNER JOIN candidate_profile c ON a.candidate_profile_id = c.id
            """, nativeQuery = true)
    CandidateProfile findByApplyId(@Param("applyId") Integer applyId);

    CandidateProfile findCandidateProfileByEmail(String email);
}
