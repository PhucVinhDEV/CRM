package com.example.CRM.common.service;

import com.example.CRM.common.redis.service.RedisService;
import com.example.CRM.mail.MailAuthen.service.MailService;
import com.example.CRM.mail.MailAuthen.util.EmailSubjectEnum;
import com.example.CRM.mail.MailAuthen.util.TypeMailEnum;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

    public boolean validateOTP(String email, String inputOTP) {
        String otpEntity = getStoredOTP(email);
        // Kiểm tra nếu OTP không tồn tại
        if (otpEntity == null) {
            return false;
        }
        boolean isValid = BCrypt.checkpw(inputOTP, otpEntity);

        if (isValid) {
            // Nếu đúng, vô hiệu hóa OTP
            invalidateOTP(email);
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
