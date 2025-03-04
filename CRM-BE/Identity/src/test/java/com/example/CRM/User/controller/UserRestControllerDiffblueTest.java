package com.example.CRM.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CRM.Auth.user.controller.UserRestController;
import com.example.CRM.Auth.MailAuthen.service.MailService;
import com.example.CRM.Auth.user.model.reponsese.PublicUserDTO;
import com.example.CRM.common.model.PageReponsese;
import com.example.CRM.common.reponsese.ApiReponsese;
import com.example.CRM.common.service.ApplicationUrlService;
import com.example.CRM.redis.service.RedisService;
import com.example.CRM.Auth.role.model.Role;
import com.example.CRM.Auth.security.service.AuthenticateService;
import com.example.CRM.user.mapper.UserMapperImpl;
import com.example.CRM.Auth.user.model.User;
import com.example.CRM.Auth.user.model.record.UserRecord;
import com.example.CRM.Auth.user.repository.UserRepository;
import com.example.CRM.Auth.user.service.UserService;
import com.example.CRM.Auth.user.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserRestController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserRestControllerDiffblueTest {
    @Autowired
    private UserRestController userRestController;

    @MockBean
    private UserService userService;

    /**
     * Test {@link UserRestController#createUser(UserRecord)}.
     * <ul>
     *   <li>Then StatusCode return {@link HttpStatus}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserRestController#createUser(UserRecord)}
     */
    @Test
    @DisplayName("Test createUser(UserRecord); then StatusCode return HttpStatus")
    void testCreateUser_thenStatusCodeReturnHttpStatus() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Role role = new Role();
        role.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        role.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        role.setDescription("The characteristics of someone or something");
        role.setId(UUID.randomUUID());
        role.setIsDeleted(true);
        role.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        role.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        role.setPermissions(new HashSet<>());
        role.setRoleName("Role Name");
        role.setVersion(1);

        User user = new User();
        user.setAvatar("Avatar");
        user.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setEmail("jane.doe@example.org");
        user.setExperience("Experience");
        user.setFullName("Dr Jane Doe");
        user.setGender(User.Gender.MALE);
        UUID id = UUID.randomUUID();
        user.setId(id);
        user.setIsDeleted(true);
        user.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRole(role);
        user.setStatusVerified(User.StatusVerified.PENDING);
        user.setVersion(1);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        UserMapperImpl userMapper = new UserMapperImpl();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ApplicationUrlService applicationUrlService = new ApplicationUrlService();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisService redisService = new RedisService(redisTemplate, JsonMapper.builder().findAndAddModules().build());

        AuthenticateService authenticateService = mock(AuthenticateService.class);
        MailService mailService = mock(MailService.class);
        UserRestController userRestController = new UserRestController(
                new UserServiceImpl(userRepository, userMapper, passwordEncoder, applicationUrlService, redisService,
                        authenticateService, mailService, JsonMapper.builder().findAndAddModules().build()));

        // Act
        ResponseEntity<PublicUserDTO> actualCreateUserResult = userRestController
                .createUser(new UserRecord(UUID.randomUUID(), "jane.doe@example.org", "iloveyou", "Dr Jane Doe", "6625550144",
                        "Avatar", "Experience", User.Gender.MALE, User.StatusVerified.PENDING));

        // Assert
        verify(userRepository).save(isA(User.class));
        HttpStatusCode statusCode = actualCreateUserResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        PublicUserDTO body = actualCreateUserResult.getBody();
        assertEquals("iloveyou", body.getPassword());
        assertEquals("jane.doe@example.org", body.getEmail());
        assertEquals(201, actualCreateUserResult.getStatusCodeValue());
        assertEquals(HttpStatus.CREATED, statusCode);
        assertTrue(actualCreateUserResult.hasBody());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        assertSame(id, body.getId());
    }

    /**
     * Test {@link UserRestController#updateUser(UserRecord)}.
     * <ul>
     *   <li>Then StatusCode return {@link HttpStatus}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserRestController#updateUser(UserRecord)}
     */
    @Test
    @DisplayName("Test updateUser(UserRecord); then StatusCode return HttpStatus")
    void testUpdateUser_thenStatusCodeReturnHttpStatus() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Role role = new Role();
        role.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        role.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        role.setDescription("The characteristics of someone or something");
        role.setId(UUID.randomUUID());
        role.setIsDeleted(true);
        role.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        role.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        role.setPermissions(new HashSet<>());
        role.setRoleName("Role Name");
        role.setVersion(1);

        User user = new User();
        user.setAvatar("Avatar");
        user.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setEmail("jane.doe@example.org");
        user.setExperience("Experience");
        user.setFullName("Dr Jane Doe");
        user.setGender(User.Gender.MALE);
        user.setId(UUID.randomUUID());
        user.setIsDeleted(true);
        user.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRole(role);
        user.setStatusVerified(User.StatusVerified.PENDING);
        user.setVersion(1);
        Optional<User> ofResult = Optional.of(user);

        Role role2 = new Role();
        role2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        role2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        role2.setDescription("The characteristics of someone or something");
        role2.setId(UUID.randomUUID());
        role2.setIsDeleted(true);
        role2.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        role2.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        role2.setPermissions(new HashSet<>());
        role2.setRoleName("Role Name");
        role2.setVersion(1);

        User user2 = new User();
        user2.setAvatar("Avatar");
        user2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        user2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user2.setEmail("jane.doe@example.org");
        user2.setExperience("Experience");
        user2.setFullName("Dr Jane Doe");
        user2.setGender(User.Gender.MALE);
        UUID id = UUID.randomUUID();
        user2.setId(id);
        user2.setIsDeleted(true);
        user2.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        user2.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setRole(role2);
        user2.setStatusVerified(User.StatusVerified.PENDING);
        user2.setVersion(1);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        UserMapperImpl userMapper = new UserMapperImpl();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ApplicationUrlService applicationUrlService = new ApplicationUrlService();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisService redisService = new RedisService(redisTemplate, JsonMapper.builder().findAndAddModules().build());

        AuthenticateService authenticateService = mock(AuthenticateService.class);
        MailService mailService = mock(MailService.class);
        UserRestController userRestController = new UserRestController(
                new UserServiceImpl(userRepository, userMapper, passwordEncoder, applicationUrlService, redisService,
                        authenticateService, mailService, JsonMapper.builder().findAndAddModules().build()));

        // Act
        ResponseEntity<PublicUserDTO> actualUpdateUserResult = userRestController
                .updateUser(new UserRecord(UUID.randomUUID(), "jane.doe@example.org", "iloveyou", "Dr Jane Doe", "6625550144",
                        "Avatar", "Experience", User.Gender.MALE, User.StatusVerified.PENDING));

        // Assert
        verify(userRepository).findById(isA(UUID.class));
        verify(userRepository).save(isA(User.class));
        HttpStatusCode statusCode = actualUpdateUserResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        PublicUserDTO body = actualUpdateUserResult.getBody();
        assertEquals("iloveyou", body.getPassword());
        assertEquals("jane.doe@example.org", body.getEmail());
        assertEquals(200, actualUpdateUserResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(actualUpdateUserResult.hasBody());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
        assertSame(id, body.getId());
    }

    /**
     * Test {@link UserRestController#getUsers(int, int, String, String)}.
     * <ul>
     *   <li>Then return Message is {@code null}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link UserRestController#getUsers(int, int, String, String)}
     */
    @Test
    @DisplayName("Test getUsers(int, int, String, String); then return Message is 'null'")
    void testGetUsers_thenReturnMessageIsNull() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        UserMapperImpl userMapper = new UserMapperImpl();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ApplicationUrlService applicationUrlService = new ApplicationUrlService();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisService redisService = new RedisService(redisTemplate, JsonMapper.builder().findAndAddModules().build());

        AuthenticateService authenticateService = mock(AuthenticateService.class);
        MailService mailService = mock(MailService.class);

        // Act
        ApiReponsese<PageReponsese<PublicUserDTO>> actualUsers = (new UserRestController(
                new UserServiceImpl(userRepository, userMapper, passwordEncoder, applicationUrlService, redisService,
                        authenticateService, mailService, JsonMapper.builder().findAndAddModules().build()))).getUsers(3, 1,
                "Sort By", "Sort Direction");

        // Assert
        verify(userRepository).findAll(isA(Pageable.class));
        assertNull(actualUsers.getMessage());
        assertNull(actualUsers.getErrors());
        PageReponsese<PublicUserDTO> result = actualUsers.getResult();
        assertEquals(0, result.getNumber());
        assertEquals(0, result.getNumberOfElements());
        assertEquals(0, result.getSize());
        assertEquals(0L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(1000, actualUsers.getStatus());
        assertFalse(actualUsers.isHasErrors());
        assertTrue(result.isEmpty());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertTrue(result.getData().isEmpty());
    }

    /**
     * Test {@link UserRestController#processForgotPassword(String)}.
     * <p>
     * Method under test: {@link UserRestController#processForgotPassword(String)}
     */
    @Test
    @DisplayName("Test processForgotPassword(String)")
    @Disabled("TODO: Complete this test")
    void testProcessForgotPassword() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        when(userService.ForgotPassword(Mockito.<String>any())).thenReturn("iloveyou");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/User/forget-password")
                .param("email", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":1000,\"hasErrors\":false,\"result\":\"iloveyou\",\"timestamp\":\"17/02/2025 00:21:55\"}"));
    }

    /**
     * Test {@link UserRestController#processForgotPassword(String)}.
     * <ul>
     *   <li>Then return Result is {@code iloveyou}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserRestController#processForgotPassword(String)}
     */
    @Test
    @DisplayName("Test processForgotPassword(String); then return Result is 'iloveyou'")
    void testProcessForgotPassword_thenReturnResultIsIloveyou() throws JsonProcessingException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserService userService = mock(UserService.class);
        when(userService.ForgotPassword(Mockito.<String>any())).thenReturn("iloveyou");

        // Act
        ApiReponsese<String> actualProcessForgotPasswordResult = (new UserRestController(userService))
                .processForgotPassword("jane.doe@example.org");

        // Assert
        verify(userService).ForgotPassword(eq("jane.doe@example.org"));
        assertEquals("iloveyou", actualProcessForgotPasswordResult.getResult());
        assertNull(actualProcessForgotPasswordResult.getMessage());
        assertNull(actualProcessForgotPasswordResult.getErrors());
        assertEquals(1000, actualProcessForgotPasswordResult.getStatus());
        assertFalse(actualProcessForgotPasswordResult.isHasErrors());
    }

    /**
     * Test {@link UserRestController#getUsers(int, int, String, String)}.
     * <ul>
     *   <li>When {@link MockHttpServletRequestBuilder#param(String, String[])}
     * {@code sortDirection} is {@code foo}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link UserRestController#getUsers(int, int, String, String)}
     */
    @Test
    @DisplayName("Test getUsers(int, int, String, String); when param(String, String[]) 'sortDirection' is 'foo'")
    @Disabled("TODO: Complete this test")
    void testGetUsers_whenParamSortDirectionIsFoo() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        PageReponsese.PageReponseseBuilder<PublicUserDTO> builderResult = PageReponsese.builder();
        PageReponsese<PublicUserDTO> buildResult = builderResult.data(new ArrayList<>())
                .empty(true)
                .first(true)
                .last(true)
                .number(10)
                .numberOfElements(10)
                .size(3)
                .totalElements(1L)
                .totalPages(1)
                .build();
        when(userService.findAllByPage(Mockito.<Pageable>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/User");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("pageSize", String.valueOf(1))
                .param("sortBy", "foo")
                .param("sortDirection", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":1000,\"hasErrors\":false,\"result\":{\"data\":[],\"totalElements\":1,\"totalPages\":1,\"size\":3,"
                                        + "\"number\":10,\"first\":true,\"last\":true,\"numberOfElements\":10,\"empty\":true},\"timestamp\":\"17/02/2025"
                                        + " 00:21:18\"}"));
    }
}
