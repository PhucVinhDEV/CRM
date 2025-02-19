package com.example.CRM.Oauth2.service;

import com.example.CRM.Oauth2.Model.ExchangeTokenRequest;
import com.example.CRM.Oauth2.httpclient.OutboundIdentityClient;
import com.example.CRM.Oauth2.httpclient.OutboundUserClient;
import com.example.CRM.common.util.MessageUtil;
import com.example.CRM.security.dto.AuthenticationResponse;
import com.example.CRM.security.service.JWTService;
import com.example.CRM.user.model.User;
import com.example.CRM.user.repository.UserRepository;
import com.example.CRM.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.GRANT_TYPE;

public interface Oauth2Service {
    AuthenticationResponse OutboundService(String code) throws JsonProcessingException;
}

@Service
class Oauth2ServiceImpl implements Oauth2Service {
    @Autowired
    OutboundIdentityClient outboundIdentityClient;
    @Autowired
    OutboundUserClient outboundUserClient;
    @NonFinal
    @Value("${outbound.identity.client-id}")
    protected String OUTBOUND_IDENTITY_CLIENT_ID;
    @NonFinal
    @Value("${outbound.identity.redirect-uri}")
    protected String OUTBOUND_IDENTITY_REDIRECT_URI;
    @NonFinal
    @Value("${outbound.identity.client-secret}")
    protected String OUTBOUND_IDENTITY_CLIENT_SECRET;
    @NonFinal
    protected final String GRANT_TYPE = "authorization_code";

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;


    @Override
    @Transactional
    public AuthenticationResponse OutboundService(String code) throws JsonProcessingException {
        var response = outboundIdentityClient.exchangeToken(ExchangeTokenRequest.builder()
                .code(code)
                .clientId(OUTBOUND_IDENTITY_CLIENT_ID)
                .redirectUri(OUTBOUND_IDENTITY_REDIRECT_URI)
                .clientSecret(OUTBOUND_IDENTITY_CLIENT_SECRET)
                .grantType(GRANT_TYPE)
                .build());
        var userInfo = outboundUserClient.GetUserInfo("json",response.getAccessToken());
        Optional<User> user = userRepository.findByEmail(userInfo.getEmail());

        if (user.isPresent()) {
            User actualUser = user.get();
            return AuthenticationResponse.builder()
                    .token(jwtService.generateToken(actualUser))
                    .authenticated(true)
                    .build();
        } else {
            User actualUser = userRepository.save(
                    User.builder()
                            .email(userInfo.getEmail())
                            .avatar(userInfo.getPicture())
                            .password(null)
                            .fullName(userInfo.getFamilyName()+ " " + userInfo.getGivenName() + " " + userInfo.getName())
                            .build());
            userService.ForgotPassword(userInfo.getEmail());
            return AuthenticationResponse.builder()
                    .token(jwtService.generateToken(actualUser))
                    .authenticated(true)
                    .build();
        }

    }
}