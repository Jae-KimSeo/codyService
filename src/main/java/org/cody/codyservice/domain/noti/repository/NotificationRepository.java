package org.cody.codyservice.domain.noti.repository;

import java.util.List;

import org.cody.codyservice.domain.noti.Notification;

public interface NotificationRepository {
    Notification findById(Integer notificationId);
    List<Notification> findAll();
    List<Notification> findByProductId(Integer productId);
    Notification save(Notification notification);
} 