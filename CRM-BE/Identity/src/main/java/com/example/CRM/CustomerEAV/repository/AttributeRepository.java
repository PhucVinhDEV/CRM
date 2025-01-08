package com.example.CRM.CustomerEAV.repository;

import com.example.CRM.CustomerEAV.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute,Integer> {
    Optional<Attribute> findByAttributeName(String attributeName);
}
