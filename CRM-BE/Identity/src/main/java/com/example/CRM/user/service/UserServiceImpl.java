package com.example.CRM.user.service;

import com.example.CRM.MailAuthen.service.MailService;
import com.example.CRM.MailAuthen.util.EmailSubjectEnum;
import com.example.CRM.MailAuthen.util.TypeMailEnum;
import com.example.CRM.common.exception.AppException;
import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.common.service.ApplicationUrlService;
import com.example.CRM.common.util.MessageUtil;
import com.example.CRM.redis.service.RedisService;
import com.example.CRM.security.service.AuthenticateService;
import com.example.CRM.user.mapper.UserMapper;
import com.example.CRM.user.model.User;
import com.example.CRM.user.model.record.ChangePasswordRecord;
import com.example.CRM.user.model.record.UserRecord;
import com.example.CRM.user.model.reponsese.UserDTO;
import com.example.CRM.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
// @AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${app.reset-password-url}")
    private String resetPasswordUrl;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUrlService applicationUrlService;
    private final RedisService redisService;
    private final AuthenticateService authenticateService;
    private final MailService mailService;
    private final ObjectMapper objectMapper;

    // Constructor có tham số
    public UserServiceImpl(UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            ApplicationUrlService applicationUrlService,
            RedisService redisService,
            AuthenticateService authenticateService,
            MailService mailService,
            ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.applicationUrlService = applicationUrlService;
        this.redisService = redisService;
        this.authenticateService = authenticateService;
        this.mailService = mailService;
        this.objectMapper = objectMapper;
    }

    @Override
    public JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public GenericMapper<UserRecord, User, UserDTO> getMapper() {
        return userMapper;
    }

    @Override
    public UserDTO save(UserRecord record) {
        User user = userMapper.maptoEntity(record);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.maptoDto(userRepository.save(user));
    }

    @Override
    public String ForgotPassword(String email) throws JsonProcessingException {
        long exp = System.currentTimeMillis() + 60 * 15; // 15 phut
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(MessageUtil.EMAIL_NOT_EXIST));
        if (user != null) {
            String uuid = UUID.randomUUID().toString();
            // String url = applicationUrlService.getApplicationUrl() +
            // "/change-password?token=" + uuid;
            String url = resetPasswordUrl + uuid;

            redisService.setValueWithTTL(uuid, user.getId().toString(), exp, TimeUnit.SECONDS);
            mailService.sendWithTemplate(email, url, EmailSubjectEnum.LINK, TypeMailEnum.VERIFY_LINK);
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
        Object cacheObject = redisService.getValue(id);
        UUID Userid = UUID.fromString(cacheObject.toString());
        User newUser = userRepository.findById(Userid).orElseThrow(
                () -> new AppException(MessageUtil.EMAIL_NOT_EXIST));
        newUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(newUser);
        return true;
    }
}
