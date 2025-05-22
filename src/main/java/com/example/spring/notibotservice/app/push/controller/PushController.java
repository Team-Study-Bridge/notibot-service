package com.example.spring.notibotservice.app.push.controller;

import com.example.spring.notibotservice.app.push.dto.PushRequest;
import com.example.spring.notibotservice.app.push.service.PushCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/noti/push")
public class PushController {

    private final PushCommandService pushCommandService;

    /**
     * 즉시 푸시 발송 요청
     */
    @PostMapping
    public ResponseEntity<String> sendPush(@RequestBody PushRequest request) {
        pushCommandService.sendImmediatePush(request);
        return ResponseEntity.ok("푸시 발송 요청 완료");
    }

    /**
     * 예약 푸시 발송 요청
     */
    @PostMapping("/scheduled")
    public ResponseEntity<String> schedulePush(@RequestBody PushRequest request) {
        pushCommandService.schedulePush(request);
        return ResponseEntity.ok("푸시 예약 저장 완료");
    }
}
