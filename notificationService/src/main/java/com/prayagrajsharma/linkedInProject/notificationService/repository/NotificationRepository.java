package com.prayagrajsharma.linkedInProject.notificationService.repository;

import com.prayagrajsharma.linkedInProject.notificationService.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Long, Notification> {
}
