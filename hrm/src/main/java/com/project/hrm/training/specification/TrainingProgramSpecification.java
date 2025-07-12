package com.project.hrm.training.specification;

import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramFilter;
import com.project.hrm.training.entity.TrainingProgram;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TrainingProgramSpecification {
    public static Specification<TrainingProgram> filter(TrainingProgramFilter f) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (f.getTitle() != null && !f.getTitle().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + f.getTitle().toLowerCase() + "%"));
            }

            if (f.getCreatedFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("createAt"), f.getCreatedFrom().atStartOfDay()));
            }

            if (f.getCreatedTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("createAt"), f.getCreatedTo().atTime(23, 59, 59)));
            }

            if (f.getIsMandatory() != null) {
                predicates.add(cb.equal(root.get("isMandatory"), f.getIsMandatory()));
            }

            if (f.getDepartmentId() != null) {
                predicates.add(cb.equal(root.get("targetRole").get("departments").get("id"), f.getDepartmentId()));
            }

            if (f.getRoleId() != null) {
                predicates.add(cb.equal(root.get("targetRole").get("id"), f.getRoleId()));
            }

            if (f.getCreatedById() != null) {
                predicates.add(cb.equal(root.get("createBy").get("id"), f.getCreatedById()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
