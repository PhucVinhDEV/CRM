package com.example.CRM.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.CRM.Auth.user.service.UserServiceImpl;
import com.example.CRM.Auth.MailAuthen.service.MailService;
import com.example.CRM.Auth.MailAuthen.util.EmailSubjectEnum;
import com.example.CRM.Auth.MailAuthen.util.TypeMailEnum;
import com.example.CRM.common.exception.AppException;
import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.common.service.ApplicationUrlService;
import com.example.CRM.redis.service.RedisService;
import com.example.CRM.Auth.role.model.Role;
import com.example.CRM.Auth.security.service.AuthenticateService;
import com.example.CRM.Auth.user.mapper.UserMapper;
import com.example.CRM.user.mapper.UserMapperImpl;
import com.example.CRM.Auth.user.model.User;
import com.example.CRM.Auth.user.model.record.ChangePasswordRecord;
import com.example.CRM.Auth.user.model.record.UserRecord;
import com.example.CRM.Auth.user.model.reponsese.UserDTO;
import com.example.CRM.Auth.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
class UserServiceImplDiffblueTest {
    @MockBean
    private ApplicationUrlService applicationUrlService;

    @MockBean
    private AuthenticateService authenticateService;

    @MockBean
    private MailService mailService;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RedisService redisService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Test {@link UserServiceImpl#save(UserRecord)} with {@code UserRecord}.
     * <p>
     * Method under test: {@link UserServiceImpl#save(UserRecord)}
     */
    @Test
    @DisplayName("Test save(UserRecord) with 'UserRecord'")
    @Disabled("TODO: Complete this test")
    void testSaveWithUserRecord() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@3ee0300 testClass = com.example.CRM.user.service.DiffblueFakeClass233, locations = [], classes = [com.example.CRM.user.service.UserServiceImpl, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@2e752372, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@37c42058, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@fab5b39a, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@7f9681ea, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@191ef885, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@63aa7eaf], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1708)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userServiceImpl.save(new UserRecord(UUID.randomUUID(), "jane.doe@example.org", "iloveyou", "Dr Jane Doe",
                "6625550144", "Avatar", "Experience", User.Gender.MALE, User.StatusVerified.PENDING));
    }

    /**
     * Test {@link UserServiceImpl#save(UserRecord)} with {@code UserRecord}.
     * <ul>
     *   <li>Then return Password is {@code iloveyou}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserServiceImpl#save(UserRecord)}
     */
    @Test
    @DisplayName("Test save(UserRecord) with 'UserRecord'; then return Password is 'iloveyou'")
    void testSaveWithUserRecord_thenReturnPasswordIsIloveyou() {
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
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper, passwordEncoder,
                applicationUrlService, redisService, authenticateService, mailService,
                JsonMapper.builder().findAndAddModules().build());

        // Act
        UserDTO actualSaveResult = userServiceImpl
                .save(new UserRecord(UUID.randomUUID(), "jane.doe@example.org", "iloveyou", "Dr Jane Doe", "6625550144",
                        "Avatar", "Experience", User.Gender.MALE, User.StatusVerified.PENDING));

        // Assert
        verify(userRepository).save(isA(User.class));
        assertEquals("iloveyou", actualSaveResult.getPassword());
        assertEquals("jane.doe@example.org", actualSaveResult.getEmail());
        assertSame(id, actualSaveResult.getId());
    }

    /**
     * Test {@link UserServiceImpl#save(UserRecord)} with {@code UserRecord}.
     * <ul>
     *   <li>Then throw {@link AppException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserServiceImpl#save(UserRecord)}
     */
    @Test
    @DisplayName("Test save(UserRecord) with 'UserRecord'; then throw AppException")
    void testSaveWithUserRecord_thenThrowAppException() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

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
        user2.setId(UUID.randomUUID());
        user2.setIsDeleted(true);
        user2.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        user2.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setRole(role2);
        user2.setStatusVerified(User.StatusVerified.PENDING);
        user2.setVersion(1);
        UserMapper userMapper = mock(UserMapper.class);
        when(userMapper.maptoDto(Mockito.<User>any())).thenThrow(new AppException("An error occurred"));
        when(userMapper.maptoEntity(Mockito.<UserRecord>any())).thenReturn(user2);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ApplicationUrlService applicationUrlService = new ApplicationUrlService();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisService redisService = new RedisService(redisTemplate, JsonMapper.builder().findAndAddModules().build());

        AuthenticateService authenticateService = mock(AuthenticateService.class);
        MailService mailService = mock(MailService.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper, passwordEncoder,
                applicationUrlService, redisService, authenticateService, mailService,
                JsonMapper.builder().findAndAddModules().build());

        // Act and Assert
        assertThrows(AppException.class,
                () -> userServiceImpl.save(new UserRecord(UUID.randomUUID(), "jane.doe@example.org", "iloveyou", "Dr Jane Doe",
                        "6625550144", "Avatar", "Experience", User.Gender.MALE, User.StatusVerified.PENDING)));
        verify(userMapper).maptoDto(isA(User.class));
        verify(userMapper).maptoEntity(isA(UserRecord.class));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Test {@link UserServiceImpl#ForgotPassword(String)}.
     * <p>
     * Method under test: {@link UserServiceImpl#ForgotPassword(String)}
     */
    @Test
    @DisplayName("Test ForgotPassword(String)")
    void testForgotPassword() throws JsonProcessingException {
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
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(applicationUrlService.getApplicationUrl()).thenReturn("https://example.org/example");
        doNothing().when(redisService)
                .setValueWithTTL(Mockito.<String>any(), Mockito.<Object>any(), anyLong(), Mockito.<TimeUnit>any());
        when(mailService.sendWithTemplate(Mockito.<String>any(), Mockito.<String>any(), Mockito.<EmailSubjectEnum>any(),
                Mockito.<TypeMailEnum>any())).thenThrow(new AppException("An error occurred"));

        // Act and Assert
        assertThrows(AppException.class, () -> userServiceImpl.ForgotPassword("jane.doe@example.org"));
        verify(mailService).sendWithTemplate(eq("jane.doe@example.org"),
                eq("https://example.org/example/change-password?token=bf1d6199-dd7f-490d-86be-476e00b541d0"),
                eq(EmailSubjectEnum.LINK), eq(TypeMailEnum.VERIFY_LINK));
        verify(applicationUrlService).getApplicationUrl();
        verify(redisService).setValueWithTTL(eq("bf1d6199-dd7f-490d-86be-476e00b541d0"), isA(Object.class), eq(900L),
                eq(TimeUnit.SECONDS));
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
    }

    /**
     * Test {@link UserServiceImpl#ForgotPassword(String)}.
     * <ul>
     *   <li>Given {@link UserRepository} {@link UserRepository#findByEmail(String)}
     * return empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserServiceImpl#ForgotPassword(String)}
     */
    @Test
    @DisplayName("Test ForgotPassword(String); given UserRepository findByEmail(String) return empty")
    void testForgotPassword_givenUserRepositoryFindByEmailReturnEmpty() throws JsonProcessingException {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(AppException.class, () -> userServiceImpl.ForgotPassword("jane.doe@example.org"));
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
    }

    /**
     * Test {@link UserServiceImpl#ForgotPassword(String)}.
     * <ul>
     *   <li>Then return {@code Mail Authentication SUCCESFULLY}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserServiceImpl#ForgotPassword(String)}
     */
    @Test
    @DisplayName("Test ForgotPassword(String); then return 'Mail Authentication SUCCESFULLY'")
    void testForgotPassword_thenReturnMailAuthenticationSuccesfully() throws JsonProcessingException {
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
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(applicationUrlService.getApplicationUrl()).thenReturn("https://example.org/example");
        doNothing().when(redisService)
                .setValueWithTTL(Mockito.<String>any(), Mockito.<Object>any(), anyLong(), Mockito.<TimeUnit>any());
        when(mailService.sendWithTemplate(Mockito.<String>any(), Mockito.<String>any(), Mockito.<EmailSubjectEnum>any(),
                Mockito.<TypeMailEnum>any())).thenReturn(true);

        // Act
        String actualForgotPasswordResult = userServiceImpl.ForgotPassword("jane.doe@example.org");

        // Assert
        verify(mailService).sendWithTemplate(eq("jane.doe@example.org"),
                eq("https://example.org/example/change-password?token=17002174-7cf7-49c5-a720-77dd02eb6471"),
                eq(EmailSubjectEnum.LINK), eq(TypeMailEnum.VERIFY_LINK));
        verify(applicationUrlService).getApplicationUrl();
        verify(redisService).setValueWithTTL(eq("17002174-7cf7-49c5-a720-77dd02eb6471"), isA(Object.class), eq(900L),
                eq(TimeUnit.SECONDS));
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
        assertEquals("Mail Authentication SUCCESFULLY", actualForgotPasswordResult);
    }

    /**
     * Test {@link UserServiceImpl#isChangePassword(ChangePasswordRecord)}.
     * <p>
     * Method under test:
     * {@link UserServiceImpl#isChangePassword(ChangePasswordRecord)}
     */
    @Test
    @DisplayName("Test isChangePassword(ChangePasswordRecord)")
    @Disabled("TODO: Complete this test")
    void testIsChangePassword() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@3b3f0840 testClass = com.example.CRM.user.service.DiffblueFakeClass232, locations = [], classes = [com.example.CRM.user.service.UserServiceImpl, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@2e752372, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@37c42058, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@fab5b39a, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@7f9681ea, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@191ef885, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@63aa7eaf], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1708)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userServiceImpl.isChangePassword(new ChangePasswordRecord("iloveyou", "iloveyou"));
    }

    /**
     * Test {@link UserServiceImpl#isChangePassword(ChangePasswordRecord)}.
     * <ul>
     *   <li>Given {@link UserRepository} {@link CrudRepository#save(Object)} return
     * {@link User#User()}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link UserServiceImpl#isChangePassword(ChangePasswordRecord)}
     */
    @Test
    @DisplayName("Test isChangePassword(ChangePasswordRecord); given UserRepository save(Object) return User(); then return 'true'")
    void testIsChangePassword_givenUserRepositorySaveReturnUser_thenReturnTrue() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

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
        user2.setId(UUID.randomUUID());
        user2.setIsDeleted(true);
        user2.setLastModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        user2.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setRole(role2);
        user2.setStatusVerified(User.StatusVerified.PENDING);
        user2.setVersion(1);
        AuthenticateService authenticateService = mock(AuthenticateService.class);
        when(authenticateService.getAuthenticatedAccount()).thenReturn(user2);
        UserMapperImpl userMapper = new UserMapperImpl();
        LdapShaPasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
        ApplicationUrlService applicationUrlService = new ApplicationUrlService();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisService redisService = new RedisService(redisTemplate, JsonMapper.builder().findAndAddModules().build());

        MailService mailService = mock(MailService.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper, passwordEncoder,
                applicationUrlService, redisService, authenticateService, mailService,
                JsonMapper.builder().findAndAddModules().build());

        // Act
        boolean actualIsChangePasswordResult = userServiceImpl
                .isChangePassword(new ChangePasswordRecord("iloveyou", "iloveyou"));

        // Assert
        verify(authenticateService).getAuthenticatedAccount();
        verify(userRepository).save(isA(User.class));
        assertTrue(actualIsChangePasswordResult);
    }

    /**
     * Test {@link UserServiceImpl#isChangePassword(ChangePasswordRecord)}.
     * <ul>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link UserServiceImpl#isChangePassword(ChangePasswordRecord)}
     */
    @Test
    @DisplayName("Test isChangePassword(ChangePasswordRecord); then return 'false'")
    void testIsChangePassword_thenReturnFalse() {
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
        AuthenticateService authenticateService = mock(AuthenticateService.class);
        when(authenticateService.getAuthenticatedAccount()).thenReturn(user);
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ApplicationUrlService applicationUrlService = new ApplicationUrlService();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisService redisService = new RedisService(redisTemplate, JsonMapper.builder().findAndAddModules().build());

        MailService mailService = mock(MailService.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper, passwordEncoder,
                applicationUrlService, redisService, authenticateService, mailService,
                JsonMapper.builder().findAndAddModules().build());

        // Act
        boolean actualIsChangePasswordResult = userServiceImpl
                .isChangePassword(new ChangePasswordRecord("iloveyou", "iloveyou"));

        // Assert
        verify(authenticateService).getAuthenticatedAccount();
        assertFalse(actualIsChangePasswordResult);
    }

    /**
     * Test {@link UserServiceImpl#isChangePassword(ChangePasswordRecord)}.
     * <ul>
     *   <li>Then throw {@link AppException}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link UserServiceImpl#isChangePassword(ChangePasswordRecord)}
     */
    @Test
    @DisplayName("Test isChangePassword(ChangePasswordRecord); then throw AppException")
    void testIsChangePassword_thenThrowAppException() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenThrow(new AppException("An error occurred"));

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
        AuthenticateService authenticateService = mock(AuthenticateService.class);
        when(authenticateService.getAuthenticatedAccount()).thenReturn(user);
        UserMapperImpl userMapper = new UserMapperImpl();
        LdapShaPasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
        ApplicationUrlService applicationUrlService = new ApplicationUrlService();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisService redisService = new RedisService(redisTemplate, JsonMapper.builder().findAndAddModules().build());

        MailService mailService = mock(MailService.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper, passwordEncoder,
                applicationUrlService, redisService, authenticateService, mailService,
                JsonMapper.builder().findAndAddModules().build());

        // Act and Assert
        assertThrows(AppException.class,
                () -> userServiceImpl.isChangePassword(new ChangePasswordRecord("iloveyou", "iloveyou")));
        verify(authenticateService).getAuthenticatedAccount();
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Test {@link UserServiceImpl#VerifyLinkChangePassword(String, String)}.
     * <p>
     * Method under test:
     * {@link UserServiceImpl#VerifyLinkChangePassword(String, String)}
     */
    @Test
    @DisplayName("Test VerifyLinkChangePassword(String, String)")
    @Disabled("TODO: Complete this test")
    void testVerifyLinkChangePassword() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@76f4d9e4 testClass = com.example.CRM.user.service.DiffblueFakeClass231, locations = [], classes = [com.example.CRM.user.service.UserServiceImpl, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@2e752372, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@37c42058, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@fab5b39a, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@7f9681ea, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@191ef885, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@63aa7eaf], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1708)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userServiceImpl.VerifyLinkChangePassword("42", "iloveyou");
    }

    /**
     * Test getters and setters.
     * <p>
     * Methods under test:
     * <ul>
     *   <li>{@link UserServiceImpl#getMapper()}
     *   <li>{@link UserServiceImpl#getRepository()}
     * </ul>
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ApplicationUrlService applicationUrlService = new ApplicationUrlService();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisService redisService = new RedisService(redisTemplate, JsonMapper.builder().findAndAddModules().build());

        AuthenticateService authenticateService = mock(AuthenticateService.class);
        MailService mailService = mock(MailService.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper, passwordEncoder,
                applicationUrlService, redisService, authenticateService, mailService,
                JsonMapper.builder().findAndAddModules().build());

        // Act
        GenericMapper<UserRecord, User, UserDTO> actualMapper = userServiceImpl.getMapper();
        userServiceImpl.getRepository();

        // Assert
        assertTrue(actualMapper instanceof UserMapperImpl);
        assertSame(userMapper, actualMapper);
    }
}
