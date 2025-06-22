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
            if (filter.getApplicable_salary() != null) {
                predicates.add(criteriaBuilder.equal(root.get("applicable_salary"), filter.getApplicable_salary()));
            }
            if (filter.getEffective_date() != null) {
                predicates.add(criteriaBuilder.equal(root.get("effective_date"), filter.getEffective_date()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
