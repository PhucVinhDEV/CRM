package com.example.CRM.user.service;


import com.example.CRM.common.service.GenericService;

import com.example.CRM.user.model.User;

import com.example.CRM.user.model.record.ChangePasswordRecord;
import com.example.CRM.user.model.record.UserRecord;
import com.example.CRM.user.model.reponsese.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService extends GenericService<User,UserRecord, UserDTO, UUID> {
    @Override
    public UserDTO save(UserRecord record);
    boolean ForgotPassword(String email) throws JsonProcessingException;
    boolean isChangePassword(ChangePasswordRecord changePasswordRecord);
    boolean VerifyLinkChangePassword(String id, String newPassword);
}


