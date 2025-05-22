package com.example.spring.notibotservice.app.push.service;

import com.example.spring.notibotservice.app.push.dto.PushKafkaMessage;
import com.example.spring.notibotservice.app.push.dto.PushRequest;
import com.example.spring.notibotservice.notification.domain.Channel;
import com.example.spring.notibotservice.notification.domain.Notification;
import com.example.spring.notibotservice.notification.domain.Status;
import com.example.spring.notibotservice.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PushCommandService {

    private final NotificationMapper notificationMapper;
    private final PushProducerService pushProducerService;

    public void sendImmediatePush(PushRequest request) {
        Notification notification = Notification.builder()
                .channel(Channel.PUSH)
                .recipientType(request.getRecipientType())
                .title(request.getTitle())
                .content(request.getBody()) // body → content에 저장
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        notificationMapper.insertNotification(notification);

        PushKafkaMessage message = PushKafkaMessage.builder()
                .notificationId(notification.getId())
                .recipientType(request.getRecipientType())
                .build();

        pushProducerService.publishPushMessage(message);
    }

    public void schedulePush(PushRequest request) {
        // "2025-05-13T14:47:00.000Z" 형태를 안전하게 파싱
        LocalDateTime scheduleTime = Instant.parse(request.getScheduleTime())
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();

        Notification notification = Notification.builder()
                .channel(Channel.PUSH)
                .recipientType(request.getRecipientType())
                .title(request.getTitle())
                .content(request.getBody())
                .status(Status.PENDING)
                .scheduledAt(scheduleTime)
                .createdAt(LocalDateTime.now())
                .build();

        notificationMapper.insertNotification(notification);
    }
}
