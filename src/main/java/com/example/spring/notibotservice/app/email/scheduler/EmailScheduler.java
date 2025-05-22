package com.example.spring.notibotservice.app.email.scheduler;

import com.example.spring.notibotservice.app.email.dto.EmailKafkaMessage;
import com.example.spring.notibotservice.notification.domain.Channel;
import com.example.spring.notibotservice.notification.domain.Notification;
import com.example.spring.notibotservice.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailScheduler {

    private final NotificationMapper notificationMapper;
    private final KafkaTemplate<String, EmailKafkaMessage> kafkaTemplate;

    @Scheduled(fixedDelay = 60000) // 1분마다
    public void processScheduledEmails() {
        LocalDateTime now = LocalDateTime.now();

        List<Notification> dueEmails = notificationMapper
                .findPendingScheduledNotifications(now).stream()
                .filter(n -> n.getChannel() == Channel.EMAIL)
                .toList();

        for (Notification n : dueEmails) {
            EmailKafkaMessage msg = EmailKafkaMessage.builder()
                    .notificationId(n.getId())
                    .recipientType(n.getRecipientType()) // ✅ 핵심 변경
                    .build();

            log.info("⏰ 예약 이메일 Kafka 전송: {}", msg);
            kafkaTemplate.send("notification-email-topic", msg);
        }

        if (!dueEmails.isEmpty()) {
            notificationMapper.bulkUpdateStatusToQueued(
                    dueEmails.stream().map(Notification::getId).toList()
            );
        }
    }
}
