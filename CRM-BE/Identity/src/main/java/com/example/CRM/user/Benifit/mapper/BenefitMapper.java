package com.example.CRM.user.Benifit.mapper;

import com.example.CRM.user.Benifit.model.Benefit;
import com.example.CRM.user.Benifit.model.BenefitDTO;
import com.example.CRM.common.mapper.GenericMapperDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BenefitMapper extends GenericMapperDTO<Benefit, BenefitDTO> {
}
