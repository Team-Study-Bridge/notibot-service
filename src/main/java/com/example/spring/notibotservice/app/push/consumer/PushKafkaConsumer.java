package com.example.spring.notibotservice.app.push.consumer;

import com.example.spring.notibotservice.app.push.dto.PushKafkaMessage;
import com.example.spring.notibotservice.app.push.service.WebPushService;
import com.example.spring.notibotservice.common.client.AuthServiceClient;
import com.example.spring.notibotservice.common.dto.UserInfo;
import com.example.spring.notibotservice.notification.domain.Notification;
import com.example.spring.notibotservice.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PushKafkaConsumer {

    private final WebPushService webPushService;
    private final NotificationMapper notificationMapper;
    private final AuthServiceClient authServiceClient;

    @KafkaListener(topics = "notification-push-topic", groupId = "noti-group")
    public void consume(PushKafkaMessage message) {
        log.info("📲 Kafka 수신 - 푸시 발송 요청: {}", message);

        try {
            Notification notification = notificationMapper.findById(message.getNotificationId());
            if (notification == null) {
                log.error("❌ 알림 정보 없음: id={}", message.getNotificationId());
                return;
            }

            List<UserInfo> recipients = authServiceClient.getUsersByType(message.getRecipientType());

            for (UserInfo user : recipients) {
                webPushService.sendToUser(notification, user);
            }

            notificationMapper.updateStatusToSent(notification.getId(), LocalDateTime.now());
            log.info("✅ 푸시 알림 발송 완료: 알림 id={}, 대상 수={}", notification.getId(), recipients.size());

        } catch (Exception e) {
            log.error("❌ 푸시 Kafka 소비 중 예외 발생", e);
            notificationMapper.updateStatusToFailed(message.getNotificationId(), e.getMessage());
        }
    }
}
