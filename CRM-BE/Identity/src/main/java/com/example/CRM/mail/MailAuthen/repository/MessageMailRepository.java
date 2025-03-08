package com.example.CRM.mail.MailAuthen.repository;

import com.example.CRM.mail.MailAuthen.model.MessageMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageMailRepository extends JpaRepository<MessageMail,Long> {
    List<MessageMail> findByStatus(boolean status);

    @Transactional(readOnly = true)
    @Query(value = """
    SELECT * FROM j_message_email 
    WHERE status = :status 
    ORDER BY id ASC 
    LIMIT :limit OFFSET :offset
""", nativeQuery = true)
    List<MessageMail> findByStatusWithPagination(
            @Param("status") boolean status,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}
