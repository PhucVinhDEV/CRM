package com.example.CRM.user.service;

import com.example.CRM.MailAuthen.model.Mail;
import com.example.CRM.common.exception.AppException;
import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.common.service.ApplicationUrlService;
import com.example.CRM.common.util.MessageUtil;
import com.example.CRM.kafka.util.TopicUtil;
import com.example.CRM.redis.model.ResetPasswordCache;
import com.example.CRM.redis.service.RedisService;
import com.example.CRM.security.service.AuthenticateService;
import com.example.CRM.user.mapper.UserMapper;
import com.example.CRM.user.model.User;
import com.example.CRM.user.model.record.ChangePasswordRecord;
import com.example.CRM.user.model.record.UserRecord;
import com.example.CRM.user.model.reponsese.UserDTO;
import com.example.CRM.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    public boolean ForgotPassword(String email) {
        long exp = System.currentTimeMillis() + 60*15; //15 phut
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(MessageUtil.EMAIL_NOT_EXIST)
        );
        if (user != null) {
            String uuid = UUID.randomUUID().toString();
            String url = applicationUrlService.getApplicationUrl() + "/change-password?token=" + uuid;
            ResetPasswordCache cache = new ResetPasswordCache();
            cache.setToken(uuid);
            cache.setExpiryTime(new Timestamp(exp));
            cache.setUserId(user.getId());
            redisService.setValueWithTTL(uuid,cache,exp, TimeUnit.SECONDS);
        }
        return false;
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
        ResetPasswordCache cache  = (ResetPasswordCache) redisService.getValue(id);
        if (cache != null) {
            User newUser = userRepository.findById(cache.getUserId()).orElseThrow(
                    () -> new AppException(MessageUtil.EMAIL_NOT_EXIST)
            );
            newUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(newUser);
            return true;
        }
        return false;
    }
}
