package com.project.hrm.specifications;

import com.project.hrm.dto.assignmentDTO.AssignmentFilter;
import com.project.hrm.entities.Assignment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AssignmentSpecification {
    public static Specification<Assignment> filter(AssignmentFilter assignmentFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (assignmentFilter.getEmployeeId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("employees").get("id"), assignmentFilter.getEmployeeId())
                );
            }

            if (assignmentFilter.getDepartmentId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("departments").get("id"), assignmentFilter.getDepartmentId())
                );
            }

            if (assignmentFilter.getRoleId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("role").get("id"), assignmentFilter.getRoleId())
                );
            }

            if (assignmentFilter.getFromDate() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), assignmentFilter.getFromDate())
                );
            }

            if (assignmentFilter.getToDate() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), assignmentFilter.getToDate())
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
