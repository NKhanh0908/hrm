package com.project.hrm.specifications;

import com.project.hrm.dto.candidateProfileDTO.CandidateProfileFilter;
import com.project.hrm.entities.Apply;
import com.project.hrm.entities.CandidateProfile;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class CandidateProfileSpecification {
    public static Specification<CandidateProfile> filter(CandidateProfileFilter dto) {
        return (root, query, cb) -> {
            assert query != null;
            query.distinct(true);

            Join<CandidateProfile, Apply> applyJoin = root.join("apply", JoinType.LEFT);

            Predicate predicate = cb.conjunction();

            if (dto.getName() != null) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("name")), "%" + dto.getName().toLowerCase() + "%"));
            }
            if (dto.getEmail() != null) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("email")), "%" + dto.getEmail().toLowerCase() + "%"));
            }
            if (dto.getPhone() != null) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("phone")), "%" + dto.getPhone().toLowerCase() + "%"));
            }
            if (dto.getPosition() != null) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(applyJoin.get("position")), "%" + dto.getPosition().toLowerCase() + "%"));
            }
            if (dto.getRecruitmentId() != null) {
                predicate = cb.and(predicate,
                        cb.equal(applyJoin.get("recruitment").get("id"), dto.getRecruitmentId()));
            }

            return predicate;
        };
    }
}
