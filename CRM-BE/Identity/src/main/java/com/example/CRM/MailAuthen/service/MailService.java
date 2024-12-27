package com.example.CRM.MailAuthen.service;

import com.example.CRM.MailAuthen.model.Mail;
import com.example.CRM.MailAuthen.util.EmailSubjectEnum;
import com.example.CRM.MailAuthen.util.TypeMailEnum;
import com.example.CRM.common.exception.AppException;
import com.example.CRM.common.util.DateTimeUtil;
import com.example.CRM.common.util.MessageUtil;
import com.example.CRM.kafka.util.TopicUtil;
import com.example.CRM.statistic.model.StatisticDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public interface MailService {
    Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type);
}
@AllArgsConstructor
@Service
class MailServiceImpl implements MailService {


    private final KafkaTemplate kafkaTemplate;

    @Override
    public Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type) {
        try {
            String htmlContent = loadHtmlTemplate(type.getTemplate());
            Mail mail = new Mail();
            StatisticDTO statisticDTO = StatisticDTO.builder()
                    .message("Account" + email + "is Created")
                    .createdDate(DateTimeUtil.now())
                    .build();
            switch (type) {
                case OTP -> {
                    htmlContent = htmlContent.replaceAll("##otp##", content);
                    mail.setSubject(subject.getSubject());
                    mail.setBody(htmlContent);
                    mail.setTo(email);
                    sendHtmlEmail(mail,statisticDTO);
                }
                case VERIFY_LINK -> {
                    htmlContent = htmlContent.replaceAll("##verify_link##", content);
                    mail.setSubject(subject.getSubject());
                    mail.setBody(htmlContent);
                    mail.setTo(email);
                    sendHtmlEmail(mail,statisticDTO);
                }
                case PASSWORD -> {
                    htmlContent = htmlContent.replaceAll("##password##", content);
                    mail.setSubject(subject.getSubject());
                    mail.setBody(htmlContent);
                    mail.setTo(email);
                    sendHtmlEmail(mail,statisticDTO);
                }
            }
        } catch (MessagingException e) {
            throw new AppException( e.getMessage());
        }
        return true;

    }
    private void sendHtmlEmail(Mail mail, StatisticDTO statisticDTO) throws MessagingException {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TopicUtil.NOTIFICATION_TOPIC, mail);
//        CompletableFuture<SendResult<String, Object>> future1 = kafkaTemplate.send(TopicUtil.STATISTIC_TOPIC, statisticDTO);

        // Add a callback to handle success and failure scenarios using CompletableFuture API
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                // Handle success, log partition info
                System.out.println("Message sent successfully to partition: " + result.getRecordMetadata().partition());
            } else {
                // Handle failure, log error
                System.err.println("Message failed to send: " + ex.getMessage());
            }
        });

//        future1.whenComplete((result, ex) -> {
//            if (ex == null) {
//                // Handle success, log partition info
//                System.out.println("Message sent successfully to partition: " + result.getRecordMetadata().partition());
//            }else {
//                // Handle failure, log error
//                System.err.println("Message failed to send: " + ex.getMessage());
//            }
//        });

    }
    private String loadHtmlTemplate(String templateName) {
        try (InputStream inputStream = new ClassPathResource(templateName).getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }
    }
}