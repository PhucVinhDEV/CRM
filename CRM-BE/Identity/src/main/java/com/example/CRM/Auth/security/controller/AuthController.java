package com.example.CRM.Auth.security.controller;
import com.example.CRM.Auth.Oauth2.service.Oauth2Service;
import com.example.CRM.Auth.security.util.AuthorizeUtil;
import com.example.CRM.user.user.model.record.UserRecord;
import com.example.CRM.user.user.model.reponsese.PublicUserDTO;
import com.example.CRM.user.user.service.UserService;
import com.example.CRM.common.reponsese.ApiReponsese;
import com.example.CRM.common.util.DateTimeUtil;
import com.example.CRM.Auth.security.dto.AuthenticationResponse;
import com.example.CRM.Auth.security.dto.LoginRequest;
import com.example.CRM.Auth.security.service.AuthenticateService;
import com.example.CRM.Auth.security.service.JWTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.Duration;
import java.util.UUID;

@RequestMapping("/api/v1/auth")
@RestController
@Tag(name = "Auth Controller", description = "API for authentication and authorization")
public class AuthController {

    @Value("${spring.security.RefreshExperienceTime}")
    private int refreshExperienceTime;

    private final AuthenticateService authenticateService ;
    private final JWTService jwtService ;
    private final Oauth2Service oauth2Service;
    private final UserService userService;

    public AuthController(AuthenticateService authenticateService, JWTService jwtService, Oauth2Service oauth2Service, UserService userService) {
        this.authenticateService = authenticateService;
        this.jwtService = jwtService;
        this.oauth2Service = oauth2Service;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRecord record,HttpServletResponse response){
        AuthenticationResponse authenticationResponse = userService.Register(record);
        ResponseCookie cookie = ResponseCookie.from("refresh_token", authenticationResponse.getToken().getRefreshtoken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/") // Dùng "/" để áp dụng cho toàn bộ domain
                .maxAge(Duration.ofHours(refreshExperienceTime)) // Refresh Token có hạn 7 ngày
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(ApiReponsese.builder()
                .result(authenticationResponse.getToken().getAccesstoken())
                .build());
        }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
            AuthenticationResponse authenticationResponse = authenticateService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        // ✅ Set Refresh Token vào HttpOnly Cookie
        ResponseCookie cookie = ResponseCookie.from("refresh_token", authenticationResponse.getToken().getRefreshtoken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/") // Dùng "/" để áp dụng cho toàn bộ domain
                .maxAge(Duration.ofHours(refreshExperienceTime)) // Refresh Token có hạn 7 ngày
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        // ✅ Trả về Access Token trong body
        return ResponseEntity.ok(ApiReponsese.builder()
                .result(authenticationResponse.getToken().getAccesstoken())
                .build());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken,HttpServletResponse response) throws ParseException, JOSEException, JsonProcessingException {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing");
        }
        AuthenticationResponse authenticationResponse = jwtService.refeshToken(refreshToken); // Xử lý refresh token và tạo cặp token mới
        ResponseCookie cookie = ResponseCookie.from("refresh_token",  authenticationResponse.getToken().getRefreshtoken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/") // Dùng "/" để áp dụng cho toàn bộ domain
                .maxAge(Duration.ofHours(refreshExperienceTime)) // Refresh Token có hạn 7 ngày
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(ApiReponsese.builder()
                        .result(authenticationResponse.getToken().getAccesstoken())
                .build());
    }

    @GetMapping("/myself")
    @SecurityRequirement(name = "bearer-key")
    public ApiReponsese<PublicUserDTO> myseft(){
        return ApiReponsese.<PublicUserDTO>builder()
                .result(userService.getMySelf())
                .message("Get Myself Successfully")
                .build();
    }

//    @PostMapping("/login")
//    public ApiReponsese<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
//        return ApiReponsese.<AuthenticationResponse>builder()
//                .result(authenticateService.authenticate(loginRequest.getEmail(), loginRequest.getPassword()))
//                .build();
//    }

//    @PostMapping("/refresh-token")
//    @SecurityRequirement(name = "bearer-key")
//    public ApiReponsese<AuthenticationResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader) throws ParseException, JOSEException, JsonProcessingException {
//        // Lấy token từ Authorization header (cắt bỏ tiền tố "Bearer ")
//        String refreshToken = authorizationHeader.startsWith("Bearer ") ? authorizationHeader.substring(7) : authorizationHeader;
//
//        AuthenticationResponse newTokens = jwtService.refeshToken(refreshToken);  // Xử lý refresh token và tạo cặp token mới
//        return ApiReponsese.<AuthenticationResponse>builder()
//                .result(newTokens)
//                .build();
//    }

    // API logout
    @PostMapping("/logout")
    public ApiReponsese<String> logout(@RequestParam UUID userId) {
        authenticateService.Logout(userId);  // Giả sử có dịch vụ để xử lý logout, ví dụ xóa token khỏi Redis.
        return ApiReponsese.<String>builder()
                .result("Logout successful")
                .build();
    }



    @PostMapping("/verify-token")
    @SecurityRequirement(name = "bearer-key")
    public ApiReponsese<Boolean> verifyToken(@RequestHeader("Authorization") String authorizationHeader) throws ParseException, JOSEException, JsonProcessingException {
        // Lấy token từ Authorization header (cắt bỏ tiền tố "Bearer ")
        String token = authorizationHeader.startsWith("Bearer ") ? authorizationHeader.substring(7) : authorizationHeader;

        boolean isValid = false;
        if (!jwtService.verifyToken(token, false).isEmpty()) {
            isValid = true;
        }
        return ApiReponsese.<Boolean>builder()
                .result(isValid)
                .build();
    }

    @PostMapping("/outbound")
    @PreAuthorize(AuthorizeUtil.NONE)
    public ApiReponsese<AuthenticationResponse> outbound(@RequestParam String code) throws JsonProcessingException {
        return ApiReponsese.<AuthenticationResponse>builder()
                .timestamp(DateTimeUtil.now())
                .result(oauth2Service.OutboundService(code))
                .build();
    }
    @PostMapping("/forget-password")
    @PreAuthorize(AuthorizeUtil.NONE)
    public ApiReponsese<String> processForgotPassword(@RequestParam("email") String email) throws JsonProcessingException {
        return ApiReponsese.<String>builder()
                .timestamp(DateTimeUtil.now())
                .result(userService.ForgotPassword(email))
                .build();
    }
}
