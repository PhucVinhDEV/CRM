package com.example.CRM.user.user.mapper;

import com.example.CRM.user.user.model.record.UserRecord;
import com.example.CRM.user.user.model.reponsese.PublicUserDTO;
import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.user.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends GenericMapper<UserRecord, User, PublicUserDTO> {

}
