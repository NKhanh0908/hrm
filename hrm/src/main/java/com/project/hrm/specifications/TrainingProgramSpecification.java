package com.project.hrm.specifications;

import com.project.hrm.dto.trainingProgramDTO.TrainingProgramFilter;
import com.project.hrm.entities.TrainingProgram;
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
            if (f.getDurationMin() != null) {
                predicates.add(cb.ge(root.get("durationHours"), f.getDurationMin()));
            }
            if (f.getDurationMax() != null) {
                predicates.add(cb.le(root.get("durationHours"), f.getDurationMax()));
            }
            if (f.getCostMin() != null) {
                predicates.add(cb.ge(root.get("cost"), f.getCostMin()));
            }
            if (f.getCostMax() != null) {
                predicates.add(cb.le(root.get("cost"), f.getCostMax()));
            }
            if (f.getCreatedFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"), f.getCreatedFrom()));
            }
            if (f.getCreatedTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createAt"), f.getCreatedTo()));
            }
            if (f.getTrainingStatus() != null) {
                predicates.add(cb.equal(root.get("trainingStatus"), f.getTrainingStatus()));
            }
            if (f.getTrainingType() != null) {
                predicates.add(cb.equal(root.get("trainingType"), f.getTrainingType()));
            }
            if (f.getDepartmentId() != null) {
                predicates.add(cb.equal(root.get("departments").get("id"), f.getDepartmentId()));
            }
            if (f.getCreatedById() != null) {
                predicates.add(cb.equal(root.get("createBy").get("id"), f.getCreatedById()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
