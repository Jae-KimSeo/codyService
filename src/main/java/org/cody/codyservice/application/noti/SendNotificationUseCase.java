package org.cody.codyservice.application.noti;

import org.cody.codyservice.domain.noti.Notification;

public interface SendNotificationUseCase {
    Notification sendNotification(String title, String content, Integer productId);
} 