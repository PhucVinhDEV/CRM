package com.example.CRM.Auth.Oauth2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CRM.Auth.Oauth2.Model.ExchangeTokenRequest;
import com.example.CRM.Auth.Oauth2.Model.ExchangeTokenResponese;
import com.example.CRM.Auth.Oauth2.Model.OutboundUserWithTokenReponese;
import com.example.CRM.Auth.Oauth2.httpclient.OutboundIdentityClient;
import com.example.CRM.Auth.Oauth2.httpclient.OutboundUserClient;
import com.example.CRM.Auth.role.model.Role;
import com.example.CRM.Auth.security.dto.AuthenticationResponse;
import com.example.CRM.Auth.security.dto.JWTObject;
import com.example.CRM.Auth.security.service.JWTService;
import com.example.CRM.user.Benifit.model.Benefit;
import com.example.CRM.user.user.model.User;
import com.example.CRM.user.user.repository.UserRepository;
import com.example.CRM.user.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Oauth2ServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class Oauth2ServiceImplDiffblueTest {
    @MockBean
    private JWTService jWTService;

    @Autowired
    private Oauth2ServiceImpl oauth2ServiceImpl;

    @MockBean
    private OutboundIdentityClient outboundIdentityClient;

    @MockBean
    private OutboundUserClient outboundUserClient;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    /**
     * Test {@link Oauth2ServiceImpl#OutboundService(String, String, String)}.
     * <p>
     * Method under test:
     * {@link Oauth2ServiceImpl#OutboundService(String, String, String)}
     */
    @Test
    @DisplayName("Test OutboundService(String, String, String)")
    void testOutboundService() throws JsonProcessingException {
        // Arrange
        JWTObject buildResult = JWTObject.builder().accesstoken("ABC123").refreshtoken("ABC123").build();
        when(jWTService.generateToken(Mockito.<User>any())).thenReturn(buildResult);
        ExchangeTokenResponese buildResult2 = ExchangeTokenResponese.builder()
                .accessToken("ABC123")
                .expiresIn(1L)
                .refreshToken("ABC123")
                .scope("Scope")
                .tokenType("ABC123")
                .build();
        when(outboundIdentityClient.exchangeToken(Mockito.<ExchangeTokenRequest>any())).thenReturn(buildResult2);
        OutboundUserWithTokenReponese buildResult3 = OutboundUserWithTokenReponese.builder()
                .email("jane.doe@example.org")
                .familyName("Family Name")
                .givenName("Given Name")
                .id("42")
                .locale("en")
                .name("Name")
                .picture("Picture")
                .verified_email(true)
                .build();
        when(outboundUserClient.GetUserInfo(Mockito.<String>any(), Mockito.<String>any())).thenReturn(buildResult3);

        Benefit benefit = new Benefit();
        benefit.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        benefit.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benefit.setCustomer(1);
        benefit.setEmail(1);
        benefit.setId(1);
        benefit.setIsDeleted(true);
        benefit.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        benefit.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benefit.setName("Name");
        benefit.setPrice(BigInteger.valueOf(1L));
        benefit.setVersion(1);

        Role role = new Role();
        role.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        role.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        role.setDescription("The characteristics of someone or something");
        role.setId(UUID.randomUUID());
        role.setIsDeleted(true);
        role.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        role.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        role.setRoleName("Role Name");
        role.setVersion(1);

        User user = new User();
        user.setAvatar("Avatar");
        user.setBenefit(benefit);
        user.setCompany("Company");
        user.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setEmail("jane.doe@example.org");
        user.setEmailDomain("jane.doe@example.org");
        user.setEmailSignature("jane.doe@example.org");
        user.setFullName("Dr Jane Doe");
        user.setId(UUID.randomUUID());
        user.setIsDeleted(true);
        user.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRole(role);
        user.setSmtpHost("localhost");
        user.setSmtpPassword("iloveyou");
        user.setSmtpPort("Smtp Port");
        user.setSmtpUsername("janedoe");
        user.setStatusVerified(User.StatusVerified.PENDING);
        user.setUsedCustomer(1);
        user.setUsedEmail(1);
        user.setVersion(1);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        AuthenticationResponse actualOutboundServiceResult = oauth2ServiceImpl.OutboundService("Code", "Redirect URI",
                "Code Verify");

        // Assert
        verify(outboundIdentityClient).exchangeToken(isA(ExchangeTokenRequest.class));
        verify(outboundUserClient).GetUserInfo(eq("json"), eq("ABC123"));
        verify(jWTService).generateToken(isA(User.class));
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
        JWTObject token = actualOutboundServiceResult.getToken();
        assertEquals("ABC123", token.getAccesstoken());
        assertEquals("ABC123", token.getRefreshtoken());
        assertTrue(actualOutboundServiceResult.isAuthenticated());
    }
}
