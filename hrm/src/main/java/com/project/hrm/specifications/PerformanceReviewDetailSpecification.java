package com.project.hrm.specifications;

import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailFilter;
import com.project.hrm.entities.PerformanceReviewDetail;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PerformanceReviewDetailSpecification {
    public static Specification<PerformanceReviewDetail> filter(PerformanceReviewDetailFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getReviewerId() != null) {
                predicates.add(cb.equal(root.get("reviewer").get("id"), filter.getReviewerId()));
            }

            if (filter.getPerformanceReviewId() != null) {
                predicates.add(cb.equal(root.get("performanceReview").get("id"), filter.getPerformanceReviewId()));
            }

            if (filter.getCriteriaName() != null && !filter.getCriteriaName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("criteriaName")), "%" + filter.getCriteriaName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
