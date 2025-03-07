package com.example.CRM.statistic.repository;

import com.example.CRM.statistic.model.StatisticDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticDTO,Integer> {
}
