package com.example.CRM.MailAuthen.service;

import com.example.CRM.MailAuthen.model.Mail;
import com.example.CRM.MailAuthen.util.EmailSubjectEnum;
import com.example.CRM.MailAuthen.util.TypeMailEnum;
import com.example.CRM.common.exception.AppException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public interface MailService {
    Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type);
}
@AllArgsConstructor
class MailServiceImpl implements MailService {
    private final KafkaTemplate kafkaTemplate;
    @Override
    public Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type) {
        return null;
    }
    private void sendHtmlEmail(String toMail, String subject, String htmlBody)  {
        Mail mail = new Mail();
        mail.setTo(toMail);
        mail.setSubject(subject);
    }
    private String loadHtmlTemplate(String templateName) {
        try (InputStream inputStream = new ClassPathResource(templateName).getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }
    }
}