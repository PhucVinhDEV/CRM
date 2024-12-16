package com.example.CRM.user.mapper;

import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.user.model.User;
import com.example.CRM.user.model.record.UserRecord;
import com.example.CRM.user.model.reponsese.UserDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserRecord, User, UserDTO> {

}
