package com.example.spring.notibotservice.notification.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface NotificationDeliveryLogMapper {

    void insertLog(
            @Param("notificationId") Long notificationId,
            @Param("userId") Long userId,
            @Param("status") String status,
            @Param("errorMessage") String errorMessage,
            @Param("sentAt") LocalDateTime sentAt
    );
}