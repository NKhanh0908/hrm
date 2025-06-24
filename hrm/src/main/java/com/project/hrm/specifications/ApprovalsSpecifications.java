package com.project.hrm.specifications;

import com.project.hrm.dto.approvalsDTO.ApprovalsFilter;
import com.project.hrm.entities.Approvals;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ApprovalsSpecifications {

    public static Specification<Approvals> filter(ApprovalsFilter filter) {
        return (root, query, cb) -> {
            Specification<Approvals> spec = Specification.where(null);

            if (filter.getEmployeeReviewId() != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("employeeReview").get("id"), filter.getEmployeeReviewId()));
            }

            if (filter.getPayrollId() != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("payroll").get("id"), filter.getPayrollId()));
            }

            if (filter.getApprovalDate() != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("approvalDate"), filter.getApprovalDate()));
            }

            if (filter.getComment() != null && !filter.getComment().isBlank()) {
                spec = spec.and((r, q, c) -> c.like(c.lower(r.get("comment")), "%" + filter.getComment().toLowerCase() + "%"));
            }

            if (filter.getStatus() != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("status"), filter.getStatus()));
            }

            return spec.toPredicate(root, query, cb);
        };
    }

    public static Specification<Approvals> filterByApprovalDateRange(LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, cb) -> {
            if (fromDate != null && toDate != null) {
                return cb.between(root.get("approvalDate"), fromDate, toDate);
            } else if (fromDate != null) {
                return cb.greaterThanOrEqualTo(root.get("approvalDate"), fromDate);
            } else if (toDate != null) {
                return cb.lessThanOrEqualTo(root.get("approvalDate"), toDate);
            } else {
                return cb.conjunction(); // không lọc nếu không có ngày
            }
        };
    }
}
