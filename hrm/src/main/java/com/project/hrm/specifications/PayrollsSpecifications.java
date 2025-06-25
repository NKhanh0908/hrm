package com.project.hrm.specifications;

import com.project.hrm.dto.payrollsDTO.PayrollsFilter;
import com.project.hrm.dto.payrollsDTO.PayrollsFilterWithRange;
import com.project.hrm.entities.Payrolls;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PayrollsSpecifications {

    public static Specification<Payrolls> filter(PayrollsFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getEmployeeId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("employee").get("id"), filter.getEmployeeId()));
            }

            if (filter.getPayPeriodId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("payPeriod").get("id"), filter.getPayPeriodId()));
            }

            if (filter.getTotalIncome() != null) {
                predicates.add(criteriaBuilder.equal(root.get("total_income"), filter.getTotalIncome()));
            }

            if (filter.getTotalDeduction() != null) {
                predicates.add(criteriaBuilder.equal(root.get("total_deduction"), filter.getTotalDeduction()));
            }

            if (filter.getNetSalary() != null) {
                predicates.add(criteriaBuilder.equal(root.get("net_salary"), filter.getNetSalary()));
            }

            if (filter.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filter.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Payrolls> filterWithRange(PayrollsFilterWithRange filterWithRange) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Range for tofilterWithRange.getMalIncome()
            if (filterWithRange.getMinIncome() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("total_income"), filterWithRange.getMinIncome()));
            }
            if (filterWithRange.getMaxIncome() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("total_income"), filterWithRange.getMaxIncome()));
            }

            // Range for totalDeduction
            if (filterWithRange.getMinDeduction() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("total_deduction"), filterWithRange.getMinDeduction()));
            }
            if (filterWithRange.getMaxDeduction() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("total_deduction"), filterWithRange.getMaxDeduction()));
            }

            // Range for netSalary
            if (filterWithRange.getMinNetSalary() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("net_salary"), filterWithRange.getMinNetSalary()));
            }
            if (filterWithRange.getMaxNetSalary() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("net_salary"), filterWithRange.getMaxNetSalary()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
