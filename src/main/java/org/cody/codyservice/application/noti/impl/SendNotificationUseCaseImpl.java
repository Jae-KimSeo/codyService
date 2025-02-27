package org.cody.codyservice.application.noti.impl;

import org.cody.codyservice.application.noti.SendNotificationUseCase;
import org.cody.codyservice.domain.noti.Notification;
import org.cody.codyservice.domain.noti.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendNotificationUseCaseImpl implements SendNotificationUseCase {
    
    private final NotificationRepository notificationRepository;
    
    @Autowired
    public SendNotificationUseCaseImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    @Override
    public Notification sendNotification(String title, String content, Integer productId) {
        Notification notification = new Notification(null, productId, title, content, false);
        return notificationRepository.save(notification);
    }
} 