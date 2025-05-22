package com.example.spring.notibotservice.notification.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Notification {
    private Long id;
    private Channel channel;               // EMAIL / PUSH
    private String recipientType;          // "ALL" / "STUDENTS" / "INSTRUCTORS"
    private String title;
    private String content;
    private Status status;                 // PENDING / QUEUED / SENT / FAILED
    private String errorMessage;
    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;
}
