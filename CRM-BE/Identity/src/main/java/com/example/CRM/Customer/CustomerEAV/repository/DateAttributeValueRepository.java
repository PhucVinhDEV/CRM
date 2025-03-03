package com.example.CRM.Customer.CustomerEAV.repository;

import com.example.CRM.Customer.CustomerEAV.model.DateAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateAttributeValueRepository extends JpaRepository<DateAttributeValue,Long> {
}
