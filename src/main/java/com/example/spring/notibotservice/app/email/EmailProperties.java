package com.example.spring.notibotservice.app.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aws.ses")
public class EmailProperties {
    private String region;
    private String accessKey;
    private String secretKey;
    private String senderEmail;
}
