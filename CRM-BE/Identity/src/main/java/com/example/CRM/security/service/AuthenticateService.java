package com.example.CRM.security.service;

import com.example.CRM.common.exception.AppException;
import com.example.CRM.common.util.MessageUtil;
import com.example.CRM.redis.service.RedisService;
import com.example.CRM.security.dto.AuthenticationResponse;
import com.example.CRM.security.dto.JWTObject;
import com.example.CRM.user.model.User;
import com.example.CRM.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface AuthenticateService {
    AuthenticationResponse authenticate(String username, String password);
    void Logout(UUID uuid);
    User getAuthenticatedAccount();
}

@Service
@Slf4j
@AllArgsConstructor
class AuthenticateServiceImpl implements AuthenticateService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final JWTService jwtService;
    @Autowired
    private RedisService redisService;

    @Override
    public AuthenticationResponse authenticate(String username, String password) {
        var user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException(MessageUtil.EMAIL_NOT_EXIST));
        boolean authenticated = passwordEncoder.matches(password, user.getPassword());
        if (!authenticated) {
            throw new RuntimeException(MessageUtil.PASSWORD_NOT_CORRECT);
        }

        JWTObject token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();
    }

    @Override
    public void Logout(UUID id) {
        redisService.deleteValue(id.toString());
    }

    @Override
    public User getAuthenticatedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return userRepository.findByEmail(authentication.getName()).orElseThrow(
                    () -> new AppException(MessageUtil.EMAIL_NOT_EXIST)
            );
        }else {
            throw new AppException(MessageUtil.ACCESS_TOKEN_INVALID);
        }
    }
}
