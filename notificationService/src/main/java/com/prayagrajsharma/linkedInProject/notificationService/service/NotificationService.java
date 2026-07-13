package com.prayagrajsharma.linkedInProject.notificationService.service;

import com.prayagrajsharma.linkedInProject.notificationService.entity.Notification;
import com.prayagrajsharma.linkedInProject.notificationService.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void addNotification(Notification notification) {
        log.info("Adding notification to database");
        notification = notificationRepository.save(notification);
        // Can use send mailer to send the notification on email
    }
}
