package com.example.spring.notibotservice.app.push.service;

import com.example.spring.notibotservice.app.push.dto.PushKafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushProducerService {

    private final KafkaTemplate<String, PushKafkaMessage> kafkaTemplate;

    private static final String TOPIC = "notification-push-topic";

    /**
     * 푸시 알림 Kafka 메시지 발행
     * @param message notificationId, recipientType 포함
     */
    public void publishPushMessage(PushKafkaMessage message) {
        log.info("📤 Kafka 푸시 메시지 발행: {}", message);
        kafkaTemplate.send(TOPIC, message);
    }
}
