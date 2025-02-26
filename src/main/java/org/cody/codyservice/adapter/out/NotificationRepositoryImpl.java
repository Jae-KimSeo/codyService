package org.cody.codyservice.adapter.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.cody.codyservice.domain.noti.Notification;
import org.cody.codyservice.domain.noti.repository.NotificationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    private final Map<Integer, Notification> notifications = new HashMap<>();
    
    @Override
    public Notification findById(Integer notificationId) {
        return notifications.get(notificationId);
    }
    
    @Override
    public List<Notification> findAll() {
        return new ArrayList<>(notifications.values());
    }
    
    @Override
    public List<Notification> findByProductId(Integer productId) {
        return notifications.values().stream()
                .filter(n -> n.getProductId().equals(productId))
                .collect(Collectors.toList());
    }
    
    @Override
    public Notification save(Notification notification) {
        notifications.put(notification.getNotificationId(), notification);
        return notification;
    }

} 