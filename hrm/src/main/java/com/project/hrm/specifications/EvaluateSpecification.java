package com.project.hrm.specifications;

import com.project.hrm.dto.evaluateDTO.EvaluateFilter;
import com.project.hrm.entities.Evaluate;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EvaluateSpecification {

    public static Specification<Evaluate> filter(EvaluateFilter dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getCandidateId() != null) {
                predicates.add(cb.equal(root.get("candidateProfile").get("id"), dto.getCandidateId()));
            }

            if (dto.getEmployeeId() != null) {
                predicates.add(cb.equal(root.get("createBy").get("id"), dto.getEmployeeId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
