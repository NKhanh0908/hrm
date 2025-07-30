package com.project.hrm.notification.repository;

import com.project.hrm.document.entity.DocumentAccesses;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer>, JpaSpecificationExecutor<Notification> {
    @Query(
            value = "SELECT n.* FROM notification n WHERE n.recipient_id = :recipientId ORDER BY n.created_at DESC",
            nativeQuery = true
    )
    List<Notification> findByRecipientId(@Param("recipientId") Integer recipientId);

    @Query(
            value = "SELECT n.* FROM notification n WHERE n.recipient_id = :recipientId AND n.is_read = false ORDER BY n.created_at DESC",
            nativeQuery = true
    )
    List<Notification> findAllByRecipientIdAndIsReadFalse(@Param("recipientId") Integer recipientId);

    @Query(value = """
            select n.*
            from notification n
            where n.module = :module
              and n.metadata ->> '$.id' = :id""", nativeQuery = true)
    Notification existsNotificationByReferenceId(@Param("module") String module, @Param("id") Integer id);


    @Modifying
    @Query(
            value = "UPDATE notification n SET n.is_read = true WHERE n.recipient_id = :recipientId",
            nativeQuery = true
    )
    void markAllAsRead(@Param("recipientId") Integer recipientId);


}
