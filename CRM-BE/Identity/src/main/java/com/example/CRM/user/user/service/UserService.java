package com.example.CRM.user.user.service;


import com.example.CRM.Auth.security.dto.AuthenticationResponse;
import com.example.CRM.user.user.model.record.UserRecord;
import com.example.CRM.user.user.model.reponsese.PublicUserDTO;
import com.example.CRM.common.service.GenericService;

import com.example.CRM.user.user.model.User;

import com.example.CRM.user.user.model.record.ChangePasswordRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService extends GenericService<User, UserRecord, PublicUserDTO, UUID> {
    @Override
    PublicUserDTO save(UserRecord record);
    AuthenticationResponse Register(UserRecord record);
    PublicUserDTO getMySelf();
    String ForgotPassword(String email) throws JsonProcessingException;
    boolean isChangePassword(ChangePasswordRecord changePasswordRecord);
    boolean VerifyLinkChangePassword(String id, String newPassword);
}


