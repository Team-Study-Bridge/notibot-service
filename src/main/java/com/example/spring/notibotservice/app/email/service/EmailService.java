package com.example.spring.notibotservice.app.email.service;

import com.example.spring.notibotservice.app.email.client.EmailApiClient;
import com.example.spring.notibotservice.app.email.dto.EmailSendResultDTO;
import com.example.spring.notibotservice.common.dto.UserInfo;
import com.example.spring.notibotservice.notification.domain.Notification;
import com.example.spring.notibotservice.notification.mapper.NotificationDeliveryLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailApiClient emailApiClient;
    private final NotificationDeliveryLogMapper deliveryLogMapper;
    /**
     * 개별 유저에게 이메일 발송 후 발송 로그 저장
     */
    public void sendToUser(Notification notification, UserInfo user) {
        EmailSendResultDTO result = emailApiClient.send(
                user.getEmail(),
                notification.getTitle(),
                notification.getContent()
        );

        if (result.isSuccess()) {
            deliveryLogMapper.insertLog(
                    notification.getId(),
                    user.getId(),
                    "SENT",
                    null,
                    LocalDateTime.now()
            );
        } else {
            deliveryLogMapper.insertLog(
                    notification.getId(),
                    user.getId(),
                    "FAILED",
                    result.getResponseMessage(),
                    LocalDateTime.now()
            );
        }
    }
}
