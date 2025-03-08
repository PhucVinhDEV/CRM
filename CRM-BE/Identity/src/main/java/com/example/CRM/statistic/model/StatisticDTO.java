package com.example.CRM.statistic.model;

import com.example.CRM.common.util.DateTimeUtil;
import com.example.CRM.common.util.SnowflakeIdGenerator;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "j_statistic")
public class StatisticDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String message;

    private String createdDate;

    private boolean status;

    // Khi tạo object mới, ID sẽ tự sinh từ Snowflake
    public StatisticDTO(String message, boolean status) {
        this.createdDate = DateTimeUtil.now();
        this.message = message;
        this.status = status;
    }

}
