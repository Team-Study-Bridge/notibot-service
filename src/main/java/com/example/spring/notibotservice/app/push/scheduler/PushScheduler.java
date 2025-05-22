package com.example.spring.notibotservice.app.push.scheduler;

import com.example.spring.notibotservice.app.push.dto.PushKafkaMessage;
import com.example.spring.notibotservice.app.push.service.PushProducerService;
import com.example.spring.notibotservice.notification.domain.Channel;
import com.example.spring.notibotservice.notification.domain.Notification;
import com.example.spring.notibotservice.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PushScheduler {

    private final NotificationMapper notificationMapper;
    private final PushProducerService pushProducerService;

    @Scheduled(fixedDelay = 60000) // 1분마다 실행
    public void processScheduledPushes() {
        LocalDateTime now = LocalDateTime.now();

        List<Notification> duePushes = notificationMapper.findPendingScheduledNotifications(now).stream()
                .filter(n -> n.getChannel() == Channel.PUSH)
                .toList();

        for (Notification notification : duePushes) {
            PushKafkaMessage message = PushKafkaMessage.builder()
                    .notificationId(notification.getId())
                    .recipientType(notification.getRecipientType())
                    .build();

            log.info("⏰ 예약 푸시 Kafka 전송: {}", message);
            pushProducerService.publishPushMessage(message);
        }

        if (!duePushes.isEmpty()) {
            notificationMapper.bulkUpdateStatusToQueued(
                    duePushes.stream().map(Notification::getId).toList()
            );
        }
    }
}
