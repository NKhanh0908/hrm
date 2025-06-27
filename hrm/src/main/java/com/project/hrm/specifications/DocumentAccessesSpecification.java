package com.project.hrm.specifications;

import com.project.hrm.entities.DocumentAccesses;
import com.project.hrm.enums.DocumentAccess;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;

public class DocumentAccessesSpecification {
    public static Specification<DocumentAccesses> filter(Integer documentId,
                                                         String accessLevel,
                                                         String employeeName) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            /* 1) documentId */
            if (documentId != null) {
                predicates.add(cb.equal(root.join("documents").get("id"), documentId));
            }

            /* 2) accessLevel (Enum) */
            if (StringUtils.hasText(accessLevel)) {
                try {
                    DocumentAccess level = DocumentAccess.valueOf(accessLevel.toUpperCase());
                    predicates.add(cb.equal(root.get("accessLevel"), level));
                } catch (IllegalArgumentException ignored) {
                    // accessLevel không hợp lệ => bỏ qua điều kiện
                }
            }

            /* 3) employeeName (ghép firstName + lastName) */
            if (StringUtils.hasText(employeeName)) {
                var emp = root.join("employees");
                Predicate first = cb.like(cb.lower(emp.get("firstName")),
                        "%" + employeeName.toLowerCase() + "%");
                Predicate last = cb.like(cb.lower(emp.get("lastName")),
                        "%" + employeeName.toLowerCase() + "%");
                predicates.add(cb.or(first, last));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
