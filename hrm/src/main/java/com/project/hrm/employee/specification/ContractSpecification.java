package com.project.hrm.employee.specification;

import com.project.hrm.employee.dto.contractDTO.ContractFilter;
import com.project.hrm.employee.entity.Contracts;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ContractSpecification {
    public static Specification<Contracts> filter(ContractFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getEmployeeId() != null && filter.getEmployeeId() != 0) {
                predicates.add(
                        cb.equal(root.get("employee").get("id"), filter.getEmployeeId())
                );
            }

            if (filter.getDepartmentId() != null && filter.getDepartmentId() != 0) {
                predicates.add(
                        cb.equal(root.get("role").get("departments").get("id"), filter.getDepartmentId())
                );
            }

            if (filter.getRoleId()!= null && filter.getRoleId() != 0) {
                predicates.add(
                        cb.equal(root.get("role").get("id"), filter.getRoleId())
                );
            }

            if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("title")),
                                "%" + filter.getTitle().toLowerCase() + "%"
                        )
                );
            }

            if(filter.getStatus() != null){
                predicates.add(cb.like(
                        (root.get("contract_status")),
                        "%" + filter.getStatus() + "%"
                ));
            }

            if (filter.getContractSigningDateFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("contractSigningDate"), filter.getContractSigningDateFrom()
                        )
                );
            }

            if (filter.getContractSigningDateTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("contractSigningDate"), filter.getContractSigningDateTo()
                        )
                );
            }

            if (filter.getPeriodStart() != null && filter.getPeriodEnd() != null) {
                predicates.add(
                        cb.and(
                                cb.lessThanOrEqualTo(root.get("startDate"), filter.getPeriodStart()),
                                cb.greaterThanOrEqualTo(root.get("endDate"), filter.getPeriodEnd())
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
