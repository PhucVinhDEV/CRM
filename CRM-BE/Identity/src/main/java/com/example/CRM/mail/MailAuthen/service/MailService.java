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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public interface MailService {
    Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type);
    List<MessageMail> getAllMessagesByStatus(boolean status);
}
@AllArgsConstructor
@Service
class MailServiceImpl implements MailService {

    private final StatisticRepository statisticRepository;
    private final MessageMailRepository messageMailRepository;

    @Override
    public Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type) {
        try {
            String htmlContent = loadHtmlTemplate(type.getTemplate());
            MessageMail mail = new MessageMail();
            StatisticDTO statisticDTO = new StatisticDTO();

            switch (type) {
                case OTP -> {
                    statisticDTO.setMessage("OTP" + email + "is Created");
                    statisticDTO.setStatus(false);
                    htmlContent = htmlContent.replaceAll("##otp##", content);
                    mail.setFrom("Esmart Company inc.");
                    mail.setSubject(subject.getSubject());
                    mail.setBody(htmlContent);
                    mail.setTo(email);
                    sendHtmlEmail(mail,statisticDTO);
                }
                case VERIFY_LINK -> {
                    statisticDTO.setMessage("Forgot" + email + "is Created");
                    statisticDTO.setStatus(false);
                    htmlContent = htmlContent.replaceAll("##verify_link##", content);
                    mail.setFrom("Esmart Company inc.");
                    mail.setSubject(subject.getSubject());
                    mail.setBody(htmlContent);
                    mail.setTo(email);
                    sendHtmlEmail(mail,statisticDTO);
                }
                case PASSWORD -> {
                    statisticDTO.setMessage("Forgot" + email + "is Created");
                    statisticDTO.setStatus(false);
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

    @Override
    @Transactional
    public List<MessageMail> getAllMessagesByStatus(boolean status) {
        return messageMailRepository.findByStatus(status);
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