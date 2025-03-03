package com.example.CRM.Customer.CustomerEAV.repository;

import com.example.CRM.Customer.CustomerEAV.model.StringAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StringAttributeValueRepository extends JpaRepository<StringAttributeValue, Long> {
}
