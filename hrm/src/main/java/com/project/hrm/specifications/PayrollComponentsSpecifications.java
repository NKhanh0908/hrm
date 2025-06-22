package com.project.hrm.specifications;

import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsFilter;
import com.project.hrm.entities.PayrollComponents;
import com.project.hrm.enums.PayrollComponentType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PayrollComponentsSpecifications {
    public static Specification<PayrollComponents> filter(PayrollComponentsFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Lọc theo name
            if (filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
            }

            // Lọc theo type
            if (filter.getType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), filter.getType()));
            }

            // Lọc theo amount
            if (filter.getAmount() != null) {
                predicates.add(criteriaBuilder.equal(root.get("amount"), filter.getAmount()));
            }

            // Lọc theo percentage
            if (filter.getPercentage() != null) {
                predicates.add(criteriaBuilder.equal(root.get("percentage"), filter.getPercentage()));
            }

            // Lọc theo regulation ID
            if (filter.getRegulationId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("regulation").get("id"), filter.getRegulationId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<PayrollComponents> filterWithRange(
            BigDecimal minAmount,
            BigDecimal maxAmount,
            Float minPercentage,
            Float maxPercentage,
            PayrollComponentType type
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minAmount != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), minAmount));
            }

            if (maxAmount != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("amount"), maxAmount));
            }

            if (minPercentage != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("percentage"), minPercentage));
            }

            if (maxPercentage != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("percentage"), maxPercentage));
            }

            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
