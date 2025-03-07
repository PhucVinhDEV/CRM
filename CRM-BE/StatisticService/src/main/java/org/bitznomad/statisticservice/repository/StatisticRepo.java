package org.bitznomad.statisticservice.repository;

import org.bitznomad.statisticservice.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepo extends JpaRepository<Statistic, Integer> {

}
