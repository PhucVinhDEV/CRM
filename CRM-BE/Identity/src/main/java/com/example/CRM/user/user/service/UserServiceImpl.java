package com.example.CRM.user.user.service;

import com.example.CRM.user.Benifit.repository.BenefitRepository;
import com.example.CRM.Auth.role.repository.RoleRepository;
import com.example.CRM.Auth.security.dto.AuthenticationResponse;
import com.example.CRM.user.user.model.record.ChangePasswordRecord;
import com.example.CRM.user.user.model.record.UserRecord;
import com.example.CRM.user.user.model.reponsese.PublicUserDTO;
import com.example.CRM.user.user.repository.UserRepository;
import com.example.CRM.mail.MailAuthen.service.MailService;
import com.example.CRM.mail.MailAuthen.util.EmailSubjectEnum;
import com.example.CRM.mail.MailAuthen.util.TypeMailEnum;
import com.example.CRM.common.exception.AppException;
import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.common.service.ApplicationUrlService;
import com.example.CRM.common.util.BenefitUtil;
import com.example.CRM.common.util.MessageUtil;
import com.example.CRM.common.util.RoleNameUtil;
import com.example.CRM.common.redis.service.RedisService;
import com.example.CRM.Auth.security.service.AuthenticateService;
import com.example.CRM.user.user.mapper.UserMapper;
import com.example.CRM.user.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUrlService applicationUrlService;
    private final RedisService redisService;
    private final AuthenticateService authenticateService;
    private final MailService mailService;
    private final BenefitRepository benefitRepository;
    private final RoleRepository roleRepository;

    @Override
    public JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public GenericMapper<UserRecord, User, PublicUserDTO> getMapper() {
        return userMapper;
    }


    @Override
    public PublicUserDTO save(UserRecord record) {
        User user = userMapper.maptoEntity(record);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.maptoDto(userRepository.save(user));
    }

    @Override
    public AuthenticationResponse Register(UserRecord record) {
        User user = userMapper.maptoEntity(record);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setBenefit(benefitRepository.findByName(BenefitUtil.GUEST).orElseThrow(
                () -> new RuntimeException("Benefit not found")
        ));
        user.setRole(roleRepository.findByRoleName(RoleNameUtil.MARKETER).orElseThrow(
                () -> new RuntimeException("Role not found")
        ));
        User savedUser = userRepository.save(user);
        return authenticateService.authenticate(savedUser.getEmail(),record.password());
    }

    @Override
    public PublicUserDTO getMySelf() {
        User user = authenticateService.getAuthenticatedAccount();
        return userMapper.maptoDto(user);
    }

    @Override
    public String ForgotPassword(String email) throws JsonProcessingException {
        long exp = System.currentTimeMillis() + 60*15; //15 phut
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(MessageUtil.EMAIL_NOT_EXIST)
        );
        if (user != null) {
            String uuid = UUID.randomUUID().toString();
            String url = applicationUrlService.getApplicationUrl() + "/change-password?token=" + uuid;


            redisService.setValueWithTTL(uuid,user.getId().toString(),exp, TimeUnit.SECONDS);
            mailService.sendWithTemplate(email,url, EmailSubjectEnum.LINK, TypeMailEnum.VERIFY_LINK);
        }
        return MessageUtil.MAIL_AUTHENTICATION_SUCCESS;
    }

    @Override
    public boolean isChangePassword(ChangePasswordRecord changePasswordRecord) {
        User user = authenticateService.getAuthenticatedAccount();
        if (passwordEncoder.matches(changePasswordRecord.oldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRecord.newPassword()));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean VerifyLinkChangePassword(String id, String newPassword) {
        Object cacheObject  =  redisService.getValue(id);
        UUID Userid = UUID.fromString(cacheObject.toString());
        User newUser = userRepository.findById(Userid).orElseThrow(
                () -> new AppException(MessageUtil.EMAIL_NOT_EXIST)
        );
        newUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(newUser);
        return true;
    }
}
