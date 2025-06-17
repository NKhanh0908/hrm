package com.project.hrm.specifications;


import com.project.hrm.dto.trainingSession.TrainingSessionFilter;
import com.project.hrm.entities.TrainingSession;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TrainingSessionSpecification {
    public static Specification<TrainingSession> filter(TrainingSessionFilter f) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (f.getSessionName() != null && !f.getSessionName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("sessionName")), "%" + f.getSessionName().toLowerCase() + "%"));
            }

            if (f.getTrainingProgramId() != null) {
                predicates.add(cb.equal(root.get("trainingProgram").get("id"), f.getTrainingProgramId()));
            }

            if (f.getCoordinatorId() != null) {
                predicates.add(cb.equal(root.get("coordinator").get("id"), f.getCoordinatorId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
