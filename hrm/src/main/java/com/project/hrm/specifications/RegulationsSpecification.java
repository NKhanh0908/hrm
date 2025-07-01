package com.project.hrm.specifications;

import com.project.hrm.dto.regulationsDTO.RegulationsFilter;
import com.project.hrm.entities.Regulations;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RegulationsSpecification {
    public static Specification<Regulations> filterRegulationsSpecification(RegulationsFilter filter) {
        return (root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }
            if (filter.getApplicableSalary() != null) {
                predicates.add(criteriaBuilder.equal(root.get("applicableSalary"), filter.getApplicableSalary()));
            }
            if (filter.getEffectiveDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("effectiveDate"), filter.getEffectiveDate()));
            }
            if (filter.getPayrollComponentType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), filter.getPayrollComponentType()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
