package com.example.CRM.statistic.model;

import com.example.CRM.common.util.DateTimeUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StatisticDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    private String createdDate;

    private boolean status;

    public StatisticDTO(String createdDate, String message, boolean status) {
        this.createdDate = DateTimeUtil.now();
        this.message = message;
        this.status = status;
    }

}
