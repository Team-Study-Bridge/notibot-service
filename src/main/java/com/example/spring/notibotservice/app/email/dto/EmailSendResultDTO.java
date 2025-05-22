package com.example.spring.notibotservice.app.email.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailSendResultDTO {
    private final boolean success;
    private final String responseMessage;
}