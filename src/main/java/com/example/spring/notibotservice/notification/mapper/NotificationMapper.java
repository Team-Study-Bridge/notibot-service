package com.example.spring.notibotservice.notification.mapper;

import com.example.spring.notibotservice.notification.domain.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface NotificationMapper {

    void insertNotification(Notification notification);

    void updateStatusToSent(@Param("id") Long id, @Param("sentAt") LocalDateTime sentAt);

    void updateStatusToFailed(@Param("id") Long id, @Param("errorMessage") String errorMessage);

    List<Notification> findPendingScheduledNotifications(@Param("now") LocalDateTime now);

    void bulkUpdateStatusToQueued(@Param("ids") List<Long> ids);

    Notification findById(@Param("id") Long id);

}