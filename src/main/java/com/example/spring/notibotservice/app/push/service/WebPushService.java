package com.example.spring.notibotservice.app.push.service;

import com.example.spring.notibotservice.app.push.client.FirebasePushClient;
import com.example.spring.notibotservice.app.push.mapper.PushTokenMapper;
import com.example.spring.notibotservice.common.dto.UserInfo;
import com.example.spring.notibotservice.notification.domain.Notification;
import com.example.spring.notibotservice.notification.mapper.NotificationDeliveryLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebPushService {

    private final PushTokenMapper pushTokenMapper;
    private final FirebasePushClient firebasePushClient;
    private final NotificationDeliveryLogMapper deliveryLogMapper;

    public void sendToUser(Notification notification, UserInfo user) {
        try {
            String token = pushTokenMapper.findActiveTokenByUserId(user.getId());

            if (token == null) {
                log.warn("❗ FCM 토큰 없음 → 발송 불가: userId={}", user.getId());

                deliveryLogMapper.insertLog(
                        notification.getId(),
                        user.getId(),
                        "FAILED",
                        "Token not found",
                        LocalDateTime.now()
                );
                return;
            }

            firebasePushClient.send(token, notification.getTitle(), notification.getContent());

            deliveryLogMapper.insertLog(
                    notification.getId(),
                    user.getId(),
                    "SENT",
                    null,
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            log.error("❌ 푸시 발송 실패 userId=" + user.getId(), e);
            deliveryLogMapper.insertLog(
                    notification.getId(),
                    user.getId(),
                    "FAILED",
                    e.getMessage(),
                    LocalDateTime.now()
            );
        }
    }
}
