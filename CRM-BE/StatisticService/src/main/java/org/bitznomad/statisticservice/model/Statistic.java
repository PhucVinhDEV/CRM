package org.bitznomad.statisticservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@ToString
@Getter
@Setter
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version // Hibernate sẽ kiểm tra xung đột version trước khi cập nhật
    private int version;

    private String message;

    @Temporal(TemporalType.TIMESTAMP) // Đảm bảo lưu Date dạng TIMESTAMP trong DB
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")    private Date createdDate;
    private boolean status;

    @PrePersist
    protected void onCreate() {
        if (createdDate == null) {
            createdDate = new Date(); // Đảm bảo không null khi lưu vào DB
        }
    }
}
