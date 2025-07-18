package com.project.hrm.recruitment.specification;

import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentFilter;
import com.project.hrm.recruitment.entity.Recruitment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentSpecification {
    public static Specification<Recruitment> filter(RecruitmentFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDeadlineFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("deadline"), filter.getDeadlineFrom()
                ));
            }

            if(filter.getStatus() != null){
                predicates.add(criteriaBuilder.like(
                        (root.get("status")),
                        "%" + filter.getStatus() + "%"
                ));
            }

            if (filter.getDeadlineTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("deadline"), filter.getDeadlineTo()
                ));
            }

            if (filter.getDateCreateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("createAt"), filter.getDateCreateFrom()
                ));
            }

            if (filter.getDateCreateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("createAt"), filter.getDateCreateTo()
                ));
            }

            if (filter.getRecruitmentRequirementID() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("recruitmentRequirements").get("id"), filter.getRecruitmentRequirementID()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
