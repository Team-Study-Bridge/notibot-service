package com.example.spring.notibotservice.app.push.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushRequest {
    private String recipientType;   // "ALL", "INSTRUCTORS", "STUDENTS"
    private String title;
    private String body;
    private String scheduleTime;    // null이면 즉시 발송
}
