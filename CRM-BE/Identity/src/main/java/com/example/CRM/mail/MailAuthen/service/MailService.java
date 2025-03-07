package com.example.CRM.mail.MailAuthen.service;
import com.example.CRM.mail.MailAuthen.model.MessageMail;
import com.example.CRM.mail.MailAuthen.repository.MessageMailRepository;
import com.example.CRM.mail.MailAuthen.util.EmailSubjectEnum;
import com.example.CRM.mail.MailAuthen.util.TypeMailEnum;
import com.example.CRM.common.exception.AppException;
import com.example.CRM.common.util.DateTimeUtil;
import com.example.CRM.common.kafka.util.TopicUtil;
import com.example.CRM.statistic.model.StatisticDTO;
import com.example.CRM.statistic.repository.StatisticRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
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
    private final NewTopic statistic;
    private final StatisticRepository statisticRepository;
    private final MessageMailRepository messageMailRepository;

    @Override
    public Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type) {
        try {
            String htmlContent = loadHtmlTemplate(type.getTemplate());
            MessageMail mail = new MessageMail();
            StatisticDTO statisticDTO = new StatisticDTO("Account" + email + "is Created",false);

            switch (type) {
                case OTP -> {
                    htmlContent = htmlContent.replaceAll("##otp##", content);
                    mail.setFrom("Esmart Company inc.");
                    mail.setSubject(subject.getSubject());
                    mail.setBody(htmlContent);
                    mail.setTo(email);
                    sendHtmlEmail(mail,statisticDTO);
                }
                case VERIFY_LINK -> {
                    htmlContent = htmlContent.replaceAll("##verify_link##", content);
                    mail.setFrom("Esmart Company inc.");
                    mail.setSubject(subject.getSubject());
                    mail.setBody(htmlContent);
                    mail.setTo(email);
                    sendHtmlEmail(mail,statisticDTO);
                }
                case PASSWORD -> {
                    htmlContent = htmlContent.replaceAll("##password##", content);
                    mail.setFrom("Esmart Company inc.");
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
    private void sendHtmlEmail(MessageMail mail, StatisticDTO statisticDTO) throws MessagingException {
        statisticRepository.save(statisticDTO);
        messageMailRepository.save(mail);


    }
    private String loadHtmlTemplate(String templateName) {
        try (InputStream inputStream = new ClassPathResource(templateName).getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }
    }
}