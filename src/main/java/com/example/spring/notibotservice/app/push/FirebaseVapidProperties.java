package com.example.spring.notibotservice.app.push;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "firebase.vapid")
@Getter
@Setter
public class FirebaseVapidProperties {
    private String publicKey;
    private String privateKey;
}
