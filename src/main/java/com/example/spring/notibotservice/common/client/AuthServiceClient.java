package com.example.spring.notibotservice.common.client;

import com.example.spring.notibotservice.common.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthServiceClient {

    private final WebClient authWebClient;

    public String getEmailByUserId(Long userId) {
        try {
            return authWebClient
                    .get()
                    .uri("/auths/users/{userId}/email", userId)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.createException().flatMap(Mono::error))
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            log.warn("이메일 조회 실패 userId={}", userId, e);
            return null;
        }
    }

    public List<UserInfo> getUsersByType(String recipientType) {
        try {
            return authWebClient
                    .get()
                    .uri(uriBuilder ->
                            uriBuilder.path("/auths/users/by-type")
                                    .queryParam("type", recipientType)
                                    .build())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.createException().flatMap(Mono::error))
                    .bodyToFlux(UserInfo.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            log.warn("유저 타입 조회 실패 recipientType={}", recipientType, e);
            return Collections.emptyList();
        }
    }
}