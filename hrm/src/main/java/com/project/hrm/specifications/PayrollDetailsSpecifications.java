package com.project.hrm.specifications;

import com.project.hrm.dto.payrollDetailsDTO.PayrollDetailsFilter;
import com.project.hrm.dto.payrollDetailsDTO.PayrollDetailsFilterWithRange;
import com.project.hrm.entities.PayrollDetails;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PayrollDetailsSpecifications {

    public static Specification<PayrollDetails> filter(PayrollDetailsFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getAmount() != null) {
                predicates.add(cb.equal(root.get("amount"), filter.getAmount()));
            }

            if (filter.getIsPercentage() != null) {
                predicates.add(cb.equal(root.get("isPercentage"), filter.getIsPercentage()));
            }

            if (filter.getPercentage() != null) {
                predicates.add(cb.equal(root.get("percentage"), filter.getPercentage()));
            }

            if (filter.getPayrollId() != null) {
                predicates.add(cb.equal(root.get("payroll").get("id"), filter.getPayrollId()));
            }

            if (filter.getPayrollComponentId() != null) {
                predicates.add(cb.equal(root.get("payrollComponent").get("id"), filter.getPayrollComponentId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<PayrollDetails> filterWithRange(PayrollDetailsFilterWithRange payrollDetailsFilterWithRange) {

        return (root, query, cb) -> {
            Specification<PayrollDetails> spec = Specification.where(null);

            if (payrollDetailsFilterWithRange.getMinAmount() != null) {
                spec = spec.and((r,q,c) -> c.greaterThanOrEqualTo(root.get("amount"), payrollDetailsFilterWithRange.getMinAmount()));
            }

            if (payrollDetailsFilterWithRange.getMaxAmount() != null) {
                spec = spec.and((r,q,c) -> c.lessThanOrEqualTo(root.get("amount"), payrollDetailsFilterWithRange.getMaxAmount()));
            }

            if (payrollDetailsFilterWithRange.getMinPercentage() != null) {
                spec = spec.and((r,q,c) -> c.greaterThanOrEqualTo(root.get("percentage"), payrollDetailsFilterWithRange.getMinPercentage()));
            }

            if (payrollDetailsFilterWithRange.getMaxPercentage() != null) {
                spec = spec.and((r,q,c) -> c.lessThanOrEqualTo(root.get("percentage"), payrollDetailsFilterWithRange.getMinPercentage()));
            }

            return spec.toPredicate(root, query, cb);
        };
    }

}
