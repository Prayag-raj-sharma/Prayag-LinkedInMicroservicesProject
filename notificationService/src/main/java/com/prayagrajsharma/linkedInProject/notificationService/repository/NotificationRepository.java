package com.prayagrajsharma.linkedInProject.notificationService.repository;

import com.prayagrajsharma.linkedInProject.notificationService.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
