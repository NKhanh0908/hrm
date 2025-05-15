package com.project.hrm.specifications;

import com.project.hrm.entities.Departments;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DepartmentSpecification {
    public static Specification<Departments> filterDepartment(String departmentName, String address, String email) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (departmentName != null && !departmentName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("departmentName"), "%" + departmentName + "%"));
            }

            if (address != null && !address.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }

            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
