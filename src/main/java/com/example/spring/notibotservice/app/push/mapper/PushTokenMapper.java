package com.example.spring.notibotservice.app.push.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PushTokenMapper {

    String findActiveTokenByUserId(Long userId);

    String findTokenByUserId(Long userId);

    void upsertToken(@Param("userId") Long userId, @Param("fcmToken") String fcmToken);
}