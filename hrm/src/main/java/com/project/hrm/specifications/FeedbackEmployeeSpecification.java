package com.project.hrm.specifications;

import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeFilter;
import com.project.hrm.entities.FeedbackEmployee;
import com.project.hrm.entities.PerformanceReview;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FeedbackEmployeeSpecification {
    public static Specification<FeedbackEmployee> filter(FeedbackEmployeeFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<FeedbackEmployee, PerformanceReview> reviewJoin = root.join("performanceReview");

            if (filter.getEmployeeId() != null) {
                predicates.add(cb.equal(reviewJoin.get("employee").get("id"), filter.getEmployeeId()));
            }

            if (filter.getFeedbackProviderId() != null) {
                predicates.add(cb.equal(root.get("feedbackProvider").get("id"), filter.getFeedbackProviderId()));
            }

            if (filter.getPerformanceReviewId() != null) {
                predicates.add(cb.equal(root.get("performanceReview").get("id"), filter.getPerformanceReviewId()));
            }

            if (filter.getFromCreateAt() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.getFromCreateAt()));
            }

            if (filter.getToCreateAt() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.getToCreateAt()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
