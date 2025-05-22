package com.example.spring.notibotservice.app.push.controller;

import com.example.spring.notibotservice.app.push.dto.PushTokenRegisterRequest;
import com.example.spring.notibotservice.app.push.service.PushTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/noti/user")
public class PushTokenController {

    private final PushTokenService pushTokenService;

    @PostMapping("/push-token")
    public ResponseEntity<Void> registerPushToken(
            @RequestHeader ("X-User-Id") Long userId,
            @RequestBody PushTokenRegisterRequest dto) {
        pushTokenService.saveOrUpdateToken(userId, dto.getFcmToken());

        return ResponseEntity.ok().build();
    }
}
