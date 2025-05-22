package com.example.spring.notibotservice.app.push.service;

import com.example.spring.notibotservice.app.push.mapper.PushTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PushTokenService {

    private final PushTokenMapper pushTokenMapper;

    public void saveOrUpdateToken(Long userId, String fcmToken) {
        pushTokenMapper.upsertToken(userId, fcmToken);
    }
}
