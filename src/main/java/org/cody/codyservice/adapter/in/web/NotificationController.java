package org.cody.codyservice.adapter.in.web;

import org.cody.codyservice.application.noti.MarkNotificationAsReadUseCase;
import org.cody.codyservice.application.noti.SendNotificationUseCase;
import org.cody.codyservice.common.ApiResponse;
import org.cody.codyservice.domain.noti.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    
    private final SendNotificationUseCase sendNotificationUseCase;
    private final MarkNotificationAsReadUseCase markNotificationAsReadUseCase;
    
    @Autowired
    public NotificationController(SendNotificationUseCase sendNotificationUseCase,
                                  MarkNotificationAsReadUseCase markNotificationAsReadUseCase) {
        this.sendNotificationUseCase = sendNotificationUseCase;
        this.markNotificationAsReadUseCase = markNotificationAsReadUseCase;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Notification>> sendNotification(@RequestBody NotificationRequest request) {
        Notification notification = sendNotificationUseCase.sendNotification(
                request.getTitle(), request.getContent(), request.getProductId());
        return ResponseEntity.ok(new ApiResponse<>(true, "알림이 성공적으로, 전송되었습니다.", notification));
    }
    
    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Notification>> markAsRead(@PathVariable Integer id) {
        Notification notification = markNotificationAsReadUseCase.markAsRead(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "알림이 읽음으로 표시되었습니다.", notification));
    }

    public static class NotificationRequest {
        private String title;
        private String content;
        private Integer productId;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public Integer getProductId() { return productId; }
        public void setProductId(Integer productId) { this.productId = productId; }
    }
} 