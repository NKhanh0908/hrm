package com.project.hrm.recruitment.specification;

import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementFilter;
import com.project.hrm.recruitment.entity.RecruitmentRequirements;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentRequirementsSpecification {
    public static Specification<RecruitmentRequirements> filter(
            RecruitmentRequirementFilter recruitmentRequirementFilter) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (recruitmentRequirementFilter.getDepartmentId()!= null && recruitmentRequirementFilter.getDepartmentId() != 0) {
                predicates.add(cb.equal(
                        root.get("role").get("departments").get("id"),
                        recruitmentRequirementFilter.getDepartmentId()));
            }

            if (recruitmentRequirementFilter.getRoleId()!= null && recruitmentRequirementFilter.getRoleId() != 0) {
                predicates.add(cb.equal(
                        root.get("role").get("id"),
                        recruitmentRequirementFilter.getRoleId()));
            }

            if (recruitmentRequirementFilter.getPositions() != null
                    && !recruitmentRequirementFilter.getPositions().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("positions")),
                        "%" + recruitmentRequirementFilter.getPositions().toLowerCase() + "%"));
            }

            if (recruitmentRequirementFilter.getStatus() != null) {
                predicates.add(cb.equal((root.get("status")),
                        "%" + recruitmentRequirementFilter.getStatus() + "%"));
            }

            if (recruitmentRequirementFilter.getDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("dateRequired"),
                        recruitmentRequirementFilter.getDateFrom()));
            }

            if (recruitmentRequirementFilter.getDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("dateRequired"),
                        recruitmentRequirementFilter.getDateTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
