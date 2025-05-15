package com.project.hrm.specifications;

import com.project.hrm.entities.RecruitmentRequirements;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecruitmentRequirementsSpecification {
    public static Specification<RecruitmentRequirements> filter( Integer departmentId,
                                                                 String positions,
                                                                 String status,
                                                                 LocalDateTime dateFrom,
                                                                 LocalDateTime dateTo){
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Phòng ban
            if (departmentId != null) {
                predicates.add(cb.equal(
                        root.get("departments").get("id"),
                        departmentId
                ));
            }

            // Vị trí
            if (positions != null && !positions.isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("positions")),
                        "%" + positions.toLowerCase() + "%"
                ));
            }

            // Trạng thái
            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(
                        cb.lower(root.get("status")),
                        status.toLowerCase()
                ));
            }

            // Ngày yêu cầu từ
            if (dateFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("dateRequired"),
                        dateFrom
                ));
            }

            // Ngày yêu cầu đến
            if (dateTo != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("dateRequired"),
                        dateTo
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
