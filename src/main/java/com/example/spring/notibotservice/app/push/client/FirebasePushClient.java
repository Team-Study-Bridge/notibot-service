package com.example.spring.notibotservice.app.push.client;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FirebasePushClient {

    public void send(String token, String title, String body) {
        try {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM 푸시 전송 성공: {}", response);
        } catch (Exception e) {
            log.error("FCM 푸시 전송 실패", e);
            throw new RuntimeException("FCM 전송 중 오류 발생", e);
        }
    }
}
