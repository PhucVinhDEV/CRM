package com.example.CRM.Mail.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    @NotBlank(message = "Vui lòng nhập Email gửi")
    @Email
    String from;

    @NotBlank(message = "Vui lòng nhập Email nhận")
    @Email
    String to;

    String[] cc;

    String[] bcc;

    @NotBlank(message = "Vui lòng nhập tiêu đề")
    String subject;

    @NotBlank(message = "Vui lòng nhập tin nhắn")
    String body;

    MultipartFile[] attachments;

    public Mail(String to, String subject, String body) {
        this.from = "BitzNomad@gmail.com";
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}
