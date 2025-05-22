package com.example.spring.notibotservice.app.push.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushKafkaMessage {
    private Long notificationId;     // DB에서 알림 조회용
    private String recipientType;    // "ALL", "STUDENTS", "INSTRUCTORS"
}
