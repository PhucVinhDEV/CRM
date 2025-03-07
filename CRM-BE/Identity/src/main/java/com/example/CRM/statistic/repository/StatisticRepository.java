package com.example.CRM.statistic.repository;

import com.example.CRM.statistic.model.StatisticDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticDTO,Long> {
    List<StatisticDTO> findByStatus(boolean status);
}
