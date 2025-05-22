package com.example.spring.notibotservice.app.email.consumer;

import com.example.spring.notibotservice.app.email.dto.EmailKafkaMessage;
import com.example.spring.notibotservice.app.email.service.EmailService;
import com.example.spring.notibotservice.common.client.AuthServiceClient;
import com.example.spring.notibotservice.common.dto.UserInfo;
import com.example.spring.notibotservice.notification.domain.Notification;
import com.example.spring.notibotservice.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailKafkaConsumer {

    private final EmailService emailService;
    private final NotificationMapper notificationMapper;
    private final AuthServiceClient authServiceClient;

    @KafkaListener(topics = "notification-email-topic", groupId = "notibot")
    public void consume(ConsumerRecord<String, EmailKafkaMessage> record) {
        EmailKafkaMessage message = record.value();
        log.info("📨 Kafka 수신 - 이메일 발송 요청: {}", message);

        try {
            Notification notification = notificationMapper.findById(message.getNotificationId());
            if (notification == null) {
                log.error("❌ 알림 정보 없음: id={}", message.getNotificationId());
                return;
            }

            List<UserInfo> recipients = authServiceClient.getUsersByType(message.getRecipientType());
            for (UserInfo user : recipients) {
                emailService.sendToUser(notification, user);
            }

            notificationMapper.updateStatusToSent(notification.getId(), LocalDateTime.now());
            log.info("✅ 이메일 발송 완료: 알림 id={}, 수신자 {}명", notification.getId(), recipients.size());

        } catch (Exception e) {
            log.error("❌ 이메일 Kafka 소비 중 예외 발생", e);
            notificationMapper.updateStatusToFailed(message.getNotificationId(), e.getMessage());
        }
    }
}
