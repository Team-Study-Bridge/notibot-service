package com.example.spring.notibotservice.notification.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationDeliveryLog {
    private Long id;
    private Long notificationId;
    private Long userId;
    private String status;           // "SENT", "FAILED"
    private String errorMessage;
    private LocalDateTime sentAt;
}