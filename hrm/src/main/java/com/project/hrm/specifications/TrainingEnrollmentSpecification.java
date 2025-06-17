package com.project.hrm.specifications;

import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentFilter;
import com.project.hrm.entities.TrainingEnrollment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class TrainingEnrollmentSpecification {
    public static Specification<TrainingEnrollment> filter(TrainingEnrollmentFilter filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (filter.getTrainingSessionId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("trainingSession").get("id"), filter.getTrainingSessionId()));
            }

            if (filter.getEmployeeId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("employee").get("id"), filter.getEmployeeId()));
            }

            return predicate;
        };
    }
}
