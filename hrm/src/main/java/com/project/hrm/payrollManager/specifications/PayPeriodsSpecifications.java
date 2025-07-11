package com.project.hrm.payrollManager.specifications;

import com.project.hrm.payrollManager.dto.payPeriodsDTO.PayPeriodsFilter;
import com.project.hrm.payrollManager.entities.PayPeriods;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PayPeriodsSpecifications {
    public static Specification<PayPeriods> filter(PayPeriodsFilter filter) {
        if (filter == null || allFieldsNull(filter)) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.getPayPeriodCode() != null && !filter.getPayPeriodCode().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("payPeriodCode")),
                        "%" + filter.getPayPeriodCode().toLowerCase() + "%"));
            }
            if (filter.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("startDate"), filter.getStartDate()));
            }
            if (filter.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("endDate"), filter.getEndDate()));
            }
            if (filter.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filter.getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean allFieldsNull(PayPeriodsFilter filter) {
        return filter.getPayPeriodCode() == null &&
                filter.getStartDate() == null &&
                filter.getEndDate() == null &&
                filter.getStatus() == null;
    }
}
