package com.project.hrm.specifications;


import com.project.hrm.dto.DocumentFilter.DocumentFilterDTO;
import com.project.hrm.entities.DocumentApprovals;
import com.project.hrm.enums.DocumentApprovalsStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DocumentApprovalsSpecification {

    /**
     * Trả về Specification<DocumentApprovals> để truyền vào repository:
     *    documentApprovalsRepository.findAll(
     *        DocumentApprovalsSpecification.filterDocumentApprovals(filterDto), pageable);
     */
    public static Specification<DocumentApprovals> filterDocumentApprovals(DocumentFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            /* 1) Lọc theo status (Enum) */
            if (StringUtils.hasText(filter.getStatus())) {
                try {
                    DocumentApprovalsStatus status =
                            DocumentApprovalsStatus.valueOf(filter.getStatus().toUpperCase());
                    predicates.add(criteriaBuilder.equal(
                            root.get("documentApprovalsStatus"), status));
                } catch (IllegalArgumentException e) {
                    // Nếu chuỗi không khớp enum, bạn có thể bỏ qua hoặc ném exception tùy nghiệp vụ
                }
            }

            /* 2) Lọc theo khoảng thời gian requestedAt */
            LocalDateTime start = filter.getStartDate();
            LocalDateTime end   = filter.getEndDate();
            if (start != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("requestedAt"), start));
            }
            if (end != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("requestedAt"), end));
            }

            /* 3) Lọc theo documents.id */
            if (filter.getDocumentId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.join("documents").get("id"), filter.getDocumentId()));
            }

            /* Kết quả AND tất cả điều kiện */
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
