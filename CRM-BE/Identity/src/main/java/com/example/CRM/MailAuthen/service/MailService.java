package com.example.CRM.MailAuthen.service;

import com.example.CRM.MailAuthen.util.EmailSubjectEnum;
import com.example.CRM.MailAuthen.util.TypeMailEnum;

public interface MailService {
    Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type);
}
