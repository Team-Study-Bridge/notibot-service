package com.example.spring.notibotservice.app.push.dto;

import lombok.Data;

@Data
public class PushTokenRegisterRequest {
    private String fcmToken;
}
