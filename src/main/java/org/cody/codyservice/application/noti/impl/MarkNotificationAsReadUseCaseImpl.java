package org.cody.codyservice.application.noti.impl;

import org.cody.codyservice.application.noti.MarkNotificationAsReadUseCase;
import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.noti.Notification;
import org.cody.codyservice.domain.noti.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkNotificationAsReadUseCaseImpl implements MarkNotificationAsReadUseCase {
    
    private final NotificationRepository notificationRepository;
    
    @Autowired
    public MarkNotificationAsReadUseCaseImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    @Override
    public Notification markAsRead(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId);
        if (notification == null) {
            throw new BusinessException("알림을 찾을 수 없습니다: " + notificationId);
        }
        
        notification.markAsRead();
        return notificationRepository.save(notification);
    }
} 