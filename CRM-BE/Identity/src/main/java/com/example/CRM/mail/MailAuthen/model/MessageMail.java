package com.example.CRM.mail.MailAuthen.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "J_MESSAGE_EMAIL")
public class MessageMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vui lòng nhập Email gửi")
    @Column(name = "email_from", nullable = false) // Đổi tên cột từ "from" → "email_from"
    String from;

    @NotBlank(message = "Vui lòng nhập Email nhận")
    @Email
    @Column(name = "email_to", nullable = false) // Đổi tên cột từ "from" → "email_from"
    String to;

    @NotBlank(message = "Vui lòng nhập tiêu đề")
    String subject;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Vui lòng nhập tin nhắn")
    @Basic(fetch = FetchType.LAZY)
    String body;

    private boolean status;

    public MessageMail(String to, String subject, String body) {
        this.from = "BitzNomad@gmail.com";
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

}
