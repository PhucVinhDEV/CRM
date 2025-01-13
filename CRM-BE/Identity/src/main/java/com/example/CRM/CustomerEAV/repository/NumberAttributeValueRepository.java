package com.example.CRM.CustomerEAV.repository;

import com.example.CRM.CustomerEAV.model.NumberAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberAttributeValueRepository extends JpaRepository<NumberAttributeValue, Long> {
}
