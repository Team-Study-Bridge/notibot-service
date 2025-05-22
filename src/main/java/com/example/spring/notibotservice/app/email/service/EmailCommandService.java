package com.example.spring.notibotservice.app.email.service;

import com.example.spring.notibotservice.app.email.dto.EmailKafkaMessage;
import com.example.spring.notibotservice.app.email.dto.EmailRequest;
import com.example.spring.notibotservice.notification.domain.Channel;
import com.example.spring.notibotservice.notification.domain.Notification;
import com.example.spring.notibotservice.notification.domain.Status;
import com.example.spring.notibotservice.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class EmailCommandService {

    private final NotificationMapper notificationMapper;
    private final KafkaTemplate<String, EmailKafkaMessage> kafkaTemplate;

    /**
     * 즉시 이메일 발송: 알림 저장 + Kafka 메시지 1건 발행
     */
    public void sendImmediateEmail(EmailRequest request) {
        Notification notification = Notification.builder()
                .channel(Channel.EMAIL)
                .recipientType(request.getRecipientType())
                .title(request.getSubject())
                .content(request.getContent())
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        notificationMapper.insertNotification(notification);

        EmailKafkaMessage message = EmailKafkaMessage.builder()
                .notificationId(notification.getId())
                .recipientType(request.getRecipientType())
                .build();

        kafkaTemplate.send("notification-email-topic", message);
    }

    /**
     * 예약 이메일 발송: 알림 저장만 (스케줄러가 Kafka 발행함)
     */
    public void scheduleEmail(EmailRequest request) {
        Notification notification = Notification.builder()
                .channel(Channel.EMAIL)
                .recipientType(request.getRecipientType())
                .title(request.getSubject())
                .content(request.getContent())
                .status(Status.PENDING)
                .scheduledAt(
                        Instant.parse(request.getScheduleTime())
                                .atZone(ZoneId.of("Asia/Seoul"))
                                .toLocalDateTime()
                )
                .createdAt(LocalDateTime.now())
                .build();

        notificationMapper.insertNotification(notification);
    }
}
