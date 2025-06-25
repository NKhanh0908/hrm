package com.project.hrm.specifications;

import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsFilter;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsFilterWithRange;
import com.project.hrm.dto.payrollsDTO.PayrollsFilterWithRange;
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

    public static Specification<PayrollComponents> filterWithRange(PayrollComponentsFilterWithRange filterWithRange) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterWithRange.getMinAmount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), filterWithRange.getMinAmount()));
            }

            if (filterWithRange.getMaxAmount() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("amount"), filterWithRange.getMaxAmount()));
            }

            if (filterWithRange.getMinPercentage() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("percentage"), filterWithRange.getMinPercentage()));
            }

            if (filterWithRange.getMaxPercentage() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("percentage"), filterWithRange.getMaxPercentage()));
            }

            if (filterWithRange.getType() != null) {
                predicates.add(cb.equal(root.get("type"), filterWithRange.getType()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
