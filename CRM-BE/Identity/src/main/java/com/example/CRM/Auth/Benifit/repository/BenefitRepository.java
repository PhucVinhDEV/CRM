package com.example.CRM.Auth.Benifit.repository;

import com.example.CRM.Auth.Benifit.model.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Integer> {

    boolean existsByName(String name);
}
