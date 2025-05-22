package com.example.spring.notibotservice.app.email.client;

import com.example.spring.notibotservice.app.email.EmailProperties;
import com.example.spring.notibotservice.app.email.dto.EmailSendResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Component
@RequiredArgsConstructor
public class EmailApiClient {

    private final EmailProperties props;

    public EmailSendResultDTO send(String to, String subject, String content) {
        SesClient client = SesClient.builder()
                .region(Region.of(props.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(props.getAccessKey(), props.getSecretKey())
                ))
                .build();

        Destination destination = Destination.builder()
                .toAddresses(to)
                .build();

        Content subjectContent = Content.builder().data(subject).charset("UTF-8").build();
        Content bodyContent = Content.builder().data(content).charset("UTF-8").build();

        Message message = Message.builder()
                .subject(subjectContent)
                .body(Body.builder().text(bodyContent).build())
                .build();

        SendEmailRequest request = SendEmailRequest.builder()
                .destination(destination)
                .message(message)
                .source(props.getSenderEmail())
                .build();

        try {
            client.sendEmail(request);
            return EmailSendResultDTO.builder()
                    .success(true)
                    .responseMessage("이메일 발송 성공")
                    .build();
        } catch (Exception e) {
            return EmailSendResultDTO.builder()
                    .success(false)
                    .responseMessage("이메일 발송 실패: " + e.getMessage())
                    .build();
        } finally {
            client.close();
        }
    }
}
