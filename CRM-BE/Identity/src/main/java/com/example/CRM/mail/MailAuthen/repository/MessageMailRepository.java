package com.example.CRM.mail.MailAuthen.repository;

import com.example.CRM.mail.MailAuthen.model.MessageMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMailRepository extends JpaRepository<MessageMail,Long> {
    List<MessageMail> findByStatus(boolean status);
}
