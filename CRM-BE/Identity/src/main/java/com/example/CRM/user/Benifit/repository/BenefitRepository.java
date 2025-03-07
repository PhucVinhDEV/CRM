package com.example.CRM.user.Benifit.repository;

import com.example.CRM.user.Benifit.model.Benefit;
import com.example.CRM.user.Benifit.model.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Integer> {

    boolean existsByName(String name);
    Optional<Benefit> findByName(String benefitName);
}
