package com.example.spring.notibotservice.app.email.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kafka에 발행되는 이메일 메시지.
 * 실제 발송 시점에 알림 내용을 DB에서 조회하고,
 * 수신자 목록은 auth-service를 통해 recipientType 기준으로 가져온다.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailKafkaMessage {
    private Long notificationId;     // 알림 ID (내용 DB 조회용)
    private String recipientType;    // "ALL", "STUDENTS", "INSTRUCTORS"
}
