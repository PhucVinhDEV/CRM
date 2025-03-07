package com.example.CRM.user.Benifit.restController;


import com.example.CRM.user.Benifit.mapper.BenefitMapper;
import com.example.CRM.user.Benifit.model.BenefitDTO;
import com.example.CRM.user.Benifit.service.BenefitService;
import com.example.CRM.common.reponsese.ApiReponsese;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Benefit")
@AllArgsConstructor
public class BenefitController {

    private final BenefitService benefitService;
    private final BenefitMapper benefitMapper;
    @GetMapping("/")
    public ApiReponsese<List<BenefitDTO>> getBenefit() {
        return ApiReponsese.<List<BenefitDTO>>builder()
                .result(benefitService.getAllBenefits().stream()
                        .map(benefitMapper::maptoDTO)
                        .toList())
                .build();
    }
}
