package com.example.CRM.user.controller;

import com.example.CRM.user.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@AllArgsConstructor
public class ChangePasswordController {
    private final UserService userService;

    @GetMapping("/change-password")
    public String showChangePasswordPage(@RequestParam(value = "token") String token, Model model) {
        model.addAttribute("token", token);
        return "user/change-password"; //map to file in src/main/resources/templates/
    }

    @PostMapping("/change-password")
    @Hidden
    @ResponseBody
    public String changePassword(@RequestParam String reNewPassword, @RequestParam String newPassword,
                                 @RequestParam String token) {
        log.warn(token);
        log.warn(reNewPassword);
        if (reNewPassword.equals(newPassword)) {
            if (userService.VerifyLinkChangePassword(token, reNewPassword)) {
                return "Password changed successfully!";
            }else
                return "Password changed failed!";
        }
        return "Password doesn't match!";
    }
}
