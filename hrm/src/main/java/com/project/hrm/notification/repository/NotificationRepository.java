package com.project.hrm.notification.repository;

import com.project.hrm.document.entity.DocumentAccesses;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer>, JpaSpecificationExecutor<Notification> {
    List<Notification> findAllByRecipientIdAndIsReadFalse(Integer recipient_id);

}
