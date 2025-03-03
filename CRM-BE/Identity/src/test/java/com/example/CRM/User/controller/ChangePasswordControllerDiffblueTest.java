package com.example.CRM.user.controller;

import static org.mockito.Mockito.when;

import com.example.CRM.Auth.user.controller.ChangePasswordController;
import com.example.CRM.Auth.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

@ContextConfiguration(classes = {ChangePasswordController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ChangePasswordControllerDiffblueTest {
    @Autowired
    private ChangePasswordController changePasswordController;

    @MockBean
    private UserService userService;

    /**
     * Test {@link ChangePasswordController#changePassword(String, String, String)}.
     * <ul>
     *   <li>Then content string {@code Password changed failed!}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ChangePasswordController#changePassword(String, String, String)}
     */
    @Test
    @DisplayName("Test changePassword(String, String, String); then content string 'Password changed failed!'")
    void testChangePassword_thenContentStringPasswordChangedFailed() throws Exception {
        // Arrange
        when(userService.VerifyLinkChangePassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/change-password")
                .param("newPassword", "foo")
                .param("reNewPassword", "foo")
                .param("token", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(changePasswordController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Password changed failed!"));
    }

    /**
     * Test {@link ChangePasswordController#showChangePasswordPage(String, Model)}.
     * <p>
     * Method under test:
     * {@link ChangePasswordController#showChangePasswordPage(String, Model)}
     */
    @Test
    @DisplayName("Test showChangePasswordPage(String, Model)")
    void testShowChangePasswordPage() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/change-password").param("token", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(changePasswordController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("token"))
                .andExpect(MockMvcResultMatchers.view().name("user/change-password"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/change-password"));
    }

    /**
     * Test {@link ChangePasswordController#changePassword(String, String, String)}.
     * <ul>
     *   <li>Then content string {@code Password doesn't match!}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ChangePasswordController#changePassword(String, String, String)}
     */
    @Test
    @DisplayName("Test changePassword(String, String, String); then content string 'Password doesn't match!'")
    void testChangePassword_thenContentStringPasswordDoesnTMatch() throws Exception {
        // Arrange
        when(userService.VerifyLinkChangePassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/change-password")
                .param("newPassword", "https://example.org/example")
                .param("reNewPassword", "foo")
                .param("token", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(changePasswordController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Password doesn't match!"));
    }

    /**
     * Test {@link ChangePasswordController#changePassword(String, String, String)}.
     * <ul>
     *   <li>Then content string {@code Password changed successfully!}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ChangePasswordController#changePassword(String, String, String)}
     */
    @Test
    @DisplayName("Test changePassword(String, String, String); then content string 'Password changed successfully!'")
    void testChangePassword_thenContentStringPasswordChangedSuccessfully() throws Exception {
        // Arrange
        when(userService.VerifyLinkChangePassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/change-password")
                .param("newPassword", "foo")
                .param("reNewPassword", "foo")
                .param("token", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(changePasswordController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Password changed successfully!"));
    }
}
