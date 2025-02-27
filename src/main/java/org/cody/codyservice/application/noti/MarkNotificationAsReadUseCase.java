package org.cody.codyservice.application.noti;

import org.cody.codyservice.domain.noti.Notification;

public interface MarkNotificationAsReadUseCase {
    Notification markAsRead(Integer notificationId);
} 