package com.project.hrm.specifications;

import com.project.hrm.dto.applyDTO.ApplyFilter;
import com.project.hrm.entities.Apply;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ApplySpecification {
    public static Specification<Apply> filter(ApplyFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getApplyAt() != null) {
                predicates.add(criteriaBuilder.equal(root.get("applyAt"), filter.getApplyAt()));
            }

            if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        (root.get("status")),
                        "%" + filter.getStatus() + "%"
                ));
            }

            if (filter.getPosition() != null && !filter.getPosition().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("position")),
                        "%" + filter.getPosition().toLowerCase() + "%"
                ));
            }

            if (filter.getRecruitmentID() != null) {
                predicates.add(criteriaBuilder.equal(root.get("recruitment").get("id"), filter.getRecruitmentID()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
