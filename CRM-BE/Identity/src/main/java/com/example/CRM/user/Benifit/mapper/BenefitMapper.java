package com.example.CRM.user.Benifit.mapper;

import com.example.CRM.user.Benifit.model.Benefit;
import com.example.CRM.user.Benifit.model.BenefitDTO;
import com.example.CRM.common.mapper.GenericMapperDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BenefitMapper extends GenericMapperDTO<Benefit, BenefitDTO> {
}
