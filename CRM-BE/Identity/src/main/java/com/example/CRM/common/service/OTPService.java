package com.example.CRM.common.service;

import com.example.CRM.Auth.role.model.Role;
import com.example.CRM.Auth.role.repository.RoleRepository;
import com.example.CRM.common.model.VerifyOtpRequest;
import com.example.CRM.common.redis.service.RedisService;
import com.example.CRM.common.util.BenefitUtil;
import com.example.CRM.common.util.RoleNameUtil;
import com.example.CRM.mail.MailAuthen.service.MailService;
import com.example.CRM.mail.MailAuthen.util.EmailSubjectEnum;
import com.example.CRM.mail.MailAuthen.util.TypeMailEnum;
import com.example.CRM.user.Benifit.model.Benefit;
import com.example.CRM.user.Benifit.repository.BenefitRepository;
import com.example.CRM.user.user.model.User;
import com.example.CRM.user.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class OTPService {
    private final int OTP_LENGTH = 6;
    private final int OTP_EXPIRY_MINUTES = 2;

    private final MailService mailService;
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BenefitRepository benefitRepository;
    private final PasswordEncoder passwordEncoder;
    public Void  generateAndSendOTP(String email) {
        // Sử dụng SecureRandom thay vì Random thông thường
        SecureRandom secureRandom = new SecureRandom();

        // Tạo OTP ngẫu nhiên với độ dài xác định
        StringBuilder otpBuilder = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otpBuilder.append(secureRandom.nextInt(10));
        }
        String otp = otpBuilder.toString();

        saveOTP(email, hashOTP(otp));

        // Gửi OTP qua email
        mailService.sendWithTemplate(email, otp, EmailSubjectEnum.OTP, TypeMailEnum.OTP);

        return null;
    }

    public boolean validateOTP(VerifyOtpRequest verifyOtpRequest) {
        String otpEntity = getStoredOTP(verifyOtpRequest.getEmail());
        // Kiểm tra nếu OTP không tồn tại
        if (otpEntity == null) {
            return false;
        }
        boolean isValid = BCrypt.checkpw(verifyOtpRequest.getOtp(), otpEntity);

        if (isValid) {
            // Nếu đúng, vô hiệu hóa OTP
            Role role = roleRepository.findByRoleName(RoleNameUtil.MARKETER).orElseThrow(
                    () -> new RuntimeException("Role not found"));
            Benefit benefit = benefitRepository.findByName(BenefitUtil.GUEST).orElseThrow(
                    () -> new RuntimeException("Benefit not found"));
            User user = new User(verifyOtpRequest.getEmail(),
                    verifyOtpRequest.getFullName(),
                    passwordEncoder.encode(verifyOtpRequest.getPassword())
                    ,role
                    ,benefit
            );
            userRepository.save(user);
            invalidateOTP(verifyOtpRequest.getEmail());
        }

        return isValid;
    }

    // Các hàm phụ trợ
    private String hashOTP(String otp) {
        // Implement hashing algorithm (e.g., bcrypt)
        return BCrypt.hashpw(otp, BCrypt.gensalt());
    }

    // Các phương thức tương tác với database
    private void saveOTP(String email,String hashOTP) {
        // Lưu OTP vào database
        redisService.setValueWithTTL(email,hashOTP,OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);
    }

    private String getStoredOTP(String email) {
        Object value = redisService.getValue(email);
        if (value instanceof String) {
            return (String) value;
        }
        return null; // Hoặc xử lý trường hợp giá trị không phải là String
    }

    private void invalidateOTP(String email) {
        redisService.deleteValue(email);
    }
}
