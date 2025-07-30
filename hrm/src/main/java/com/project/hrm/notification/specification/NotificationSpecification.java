package com.project.hrm.notification.specification;

import com.project.hrm.notification.dto.NotificationFilterDTO;
import com.project.hrm.notification.entity.Notification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class NotificationSpecification {
    public static Specification<Notification> isRead(Boolean isRead) {
        return (root, query, cb) -> isRead != null ? cb.equal(root.get("isRead"), isRead) : null;
    }

    public static Specification<Notification> module(String module) {
        return (root, query, cb) -> module != null ? cb.equal(root.get("module"), module) : null;
    }

    public static Specification<Notification> notificationType(String notificationType) {
        return (root, query, cb) -> notificationType != null ? cb.equal(root.get("notificationType"), notificationType) : null;
    }

    public static Specification<Notification> recipientId(Integer recipientId) {
        return (root, query, cb) -> recipientId != null ? cb.equal(root.get("recipient").get("id"), recipientId) : null;
    }

    public static Specification<Notification> senderId(Integer senderId) {
        return (root, query, cb) -> senderId != null ? cb.equal(root.get("sender").get("id"), senderId) : null;
    }

    public static Specification<Notification> fromDate(LocalDateTime fromDate) {
        return (root, query, cb) -> fromDate != null ? cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate) : null;
    }

    public static Specification<Notification> toDate(LocalDateTime toDate) {
        return (root, query, cb) -> toDate != null ? cb.lessThanOrEqualTo(root.get("createdAt"), toDate) : null;
    }
}
