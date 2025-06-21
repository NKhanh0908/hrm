package com.project.hrm.specifications;

import com.project.hrm.dto.performanceReviewDTO.PerformanceReviewFilter;
import com.project.hrm.entities.PerformanceReview;
import com.project.hrm.enums.PerformanceReviewStatus;
import com.project.hrm.enums.ReviewCycle;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PerformanceReviewSpecification {
    public static Specification<PerformanceReview> filter(PerformanceReviewFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getEmployeeId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("employee").get("id"), filter.getEmployeeId()));
            }

            if (filter.getEmployeeRequestId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("employeeRequest").get("id"), filter.getEmployeeRequestId()));
            }

            if (filter.getApproverId() != null && !filter.getApproverId().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("approver").get("id"), Integer.valueOf(filter.getApproverId())));
            }

            if (filter.getCreatedAtFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtFrom()));
            }

            if (filter.getCreatedAtTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtTo()));
            }

            if (filter.getStatus() != null && !filter.getStatus().isBlank()) {
                try {
                    predicates.add(criteriaBuilder.equal(
                            root.get("status"),
                            PerformanceReviewStatus.valueOf(filter.getStatus().toUpperCase())
                    ));
                } catch (IllegalArgumentException e) {
                    throw new CustomException(Error.INVALID_ENUM_VALUE);
                }
            }

            if (filter.getReviewCycle() != null && !filter.getReviewCycle().isBlank()) {
                try {
                    predicates.add(criteriaBuilder.equal(
                            root.get("reviewCycle"),
                            ReviewCycle.valueOf(filter.getReviewCycle().toUpperCase())
                    ));
                } catch (IllegalArgumentException e) {
                    throw new CustomException(Error.INVALID_ENUM_VALUE);
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
