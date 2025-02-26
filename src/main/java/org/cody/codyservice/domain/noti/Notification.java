package org.cody.codyservice.domain.noti;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Notification {
    private Integer notificationId;
    private Integer productId;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private Boolean isRead;

    public Notification(Integer notificationId, Integer productId, 
                       String title, String message, Boolean isRead) {
        this.notificationId = notificationId;
        this.productId = productId;
        this.title = title;
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.isRead = isRead;
    }

    public void markAsRead() {
        this.isRead = true;
    }
} 