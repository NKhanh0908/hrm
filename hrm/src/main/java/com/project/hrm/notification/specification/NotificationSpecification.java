package com.project.hrm.notification.specification;

import com.project.hrm.notification.dto.NotificationFilterDTO;
import com.project.hrm.notification.entity.Notification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class NotificationSpecification {
    public static Specification<Notification> filter(NotificationFilterDTO dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getIsRead() != null) {
                predicates.add(cb.equal(root.get("isRead"), dto.getIsRead()));
            }

            if (dto.getModule() != null) {
                predicates.add(cb.equal(root.get("module"), dto.getModule()));
            }

            if (dto.getNotificationType() != null) {
                predicates.add(cb.equal(root.get("notificationType"), dto.getNotificationType()));
            }

            if (dto.getRecipientId() != null) {
                predicates.add(cb.equal(root.get("recipient").get("id"), dto.getRecipientId()));
            }

            if (dto.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), dto.getFromDate()));
            }

            if (dto.getToDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), dto.getToDate()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
