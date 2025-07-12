package com.project.hrm.department.specification;

import com.project.hrm.department.dto.departmentDTO.DepartmentFilter;
import com.project.hrm.department.entity.Departments;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DepartmentSpecification {
    public static Specification<Departments> filterDepartment(DepartmentFilter departmentFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (departmentFilter.getDepartmentName() != null && !departmentFilter.getDepartmentName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("departmentName"), "%" + departmentFilter.getDepartmentName() + "%"));
            }

            if (departmentFilter.getAddress() != null && !departmentFilter.getAddress().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + departmentFilter.getAddress() + "%"));
            }

            if (departmentFilter.getEmail() != null && !departmentFilter.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + departmentFilter.getEmail() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
