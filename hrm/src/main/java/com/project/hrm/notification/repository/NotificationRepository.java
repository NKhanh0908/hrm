package com.project.hrm.notification.repository;

import com.project.hrm.document.entity.DocumentAccesses;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer>, JpaSpecificationExecutor<Notification> {
    @Query(
            value = "SELECT n FROM Notification n WHERE n.recipient.id = :recipientId",
            nativeQuery = true
    )
    List<Notification> findByRecipientId(@Param("recipientId") Integer recipientId);

    @Query(
            value = "SELECT n FROM Notification n WHERE n.recipient.id = :recipientId AND n.isRead = false",
            nativeQuery = true
    )
    List<Notification> findAllByRecipientIdAndIsReadFalse(@Param("recipientId") Integer recipientId);

}
