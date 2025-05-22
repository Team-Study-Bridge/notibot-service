package com.example.spring.notibotservice.app.email.controller;

import com.example.spring.notibotservice.app.email.dto.EmailKafkaMessage;
import com.example.spring.notibotservice.app.email.dto.EmailRequest;
import com.example.spring.notibotservice.app.email.service.EmailCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/noti/emails")
public class EmailController {

    private final KafkaTemplate<String, EmailKafkaMessage> kafkaTemplate;
    private final EmailCommandService emailCommandService;

    // ✅ 즉시 이메일 발송
    @PostMapping
    public ResponseEntity<String> sendEmail(
            @RequestBody EmailRequest request) {
        emailCommandService.sendImmediateEmail(request);
        return ResponseEntity.ok("즉시 이메일 발송 요청됨");
    }

    // ✅ 예약 이메일 발송
    @PostMapping("/scheduled")
    public ResponseEntity<String> scheduleEmail(
            @RequestBody EmailRequest request) {
        emailCommandService.scheduleEmail(request);
        return ResponseEntity.ok("예약 이메일 발송이 등록되었습니다");
    }
}
