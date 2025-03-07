package com.example.CRM.user.Benifit.service;

import com.example.CRM.user.Benifit.model.Benefit;
import com.example.CRM.user.Benifit.repository.BenefitRepository;
import com.example.CRM.user.Benifit.model.Benefit;import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BenefitService  {
    List<Benefit> getAllBenefits();
}

@AllArgsConstructor
@Service
class BenifitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;
    @Override
    public List<Benefit> getAllBenefits() {
        return benefitRepository.findAll();
    }
}