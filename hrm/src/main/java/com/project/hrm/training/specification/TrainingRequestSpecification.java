package com.project.hrm.training.specification;

import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestFilter;
import com.project.hrm.training.entity.TrainingRequest;
import com.project.hrm.training.enums.TrainingRequestStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class TrainingRequestSpecification {
    public static Specification<TrainingRequest> filter(TrainingRequestFilter filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // default = true

            if (filter.getRequestDate() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("requestDate"), filter.getRequestDate()));
            }

            if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("status"), TrainingRequestStatus.valueOf(filter.getStatus())));
            }

            if (filter.getTargetEmployeeId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("targetEmployee").get("id"), filter.getTargetEmployeeId()));
            }

            if (filter.getEmployeeRequestId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("requestedBy").get("id"), filter.getEmployeeRequestId()));
            }

            if (filter.getEmployeeApprovedId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("approvedBy").get("id"), filter.getEmployeeApprovedId()));
            }

            if (filter.getRequestedProgramId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("requestedProgram").get("id"), filter.getRequestedProgramId()));
            }

            return predicate;
        };
    }
}
