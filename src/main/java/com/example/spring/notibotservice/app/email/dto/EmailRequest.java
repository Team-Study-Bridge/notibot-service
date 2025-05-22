package com.example.spring.notibotservice.app.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private String recipientType; // "ALL", "INSTRUCTORS", "STUDENTS"
    private String subject;
    private String content;
    private String scheduleTime;
}
