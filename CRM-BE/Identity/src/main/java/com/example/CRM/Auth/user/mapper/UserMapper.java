package com.example.CRM.Auth.user.mapper;

import com.example.CRM.Auth.user.model.record.UserRecord;
import com.example.CRM.Auth.user.model.reponsese.UserDTO;
import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.Auth.user.model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserRecord, User, UserDTO> {

}
