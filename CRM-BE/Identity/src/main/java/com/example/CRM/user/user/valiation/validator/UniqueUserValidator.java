package com.example.CRM.user.user.valiation.validator;

import com.example.CRM.user.user.model.record.UserRecord;
import com.example.CRM.user.user.repository.UserRepository;
import com.example.CRM.user.user.valiation.anomation.UniqueUser;
import com.example.CRM.common.util.MessageUtil;
import com.example.CRM.user.user.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Optional;


public class UniqueUserValidator implements ConstraintValidator<UniqueUser, UserRecord> {
    @Autowired
    UserRepository userRepository;
    private String message;
    @Autowired
     MessageSource messageSource;

    @Override
    public void initialize(UniqueUser constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserRecord record, ConstraintValidatorContext context) {
        if (record == null) {
            buildContext(message, context);
            return false;
        }

        // Nếu ID là null => Người dùng mới => Kiểm tra tính duy nhất của email và UUID
        if (record.id() == null) {
            return isNewUserValid(record, context);
        }

        // Nếu ID không null => Người dùng cũ => Kiểm tra nếu email hoặc UUID có thay đổi
        return isOldUserValid(record, context);
    }

    private void buildContext(String message, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }

    // Kiểm tra tính duy nhất cho người dùng mới
    private boolean isNewUserValid(UserRecord record, ConstraintValidatorContext context) {
        // Kiểm tra email duy nhất
        Optional<User> emailOptional = userRepository.findByEmail(record.email());
        if (emailOptional.isPresent()) {
            message = MessageUtil.getMessage(messageSource, "user.email.exists");
            buildContext(message, context);
            return false;
        }

        return true;
    }

    // Kiểm tra tính duy nhất cho người dùng cũ (đã có ID)
    private boolean isOldUserValid(UserRecord record, ConstraintValidatorContext context) {
        Optional<User> userOptional = userRepository.findById(record.id());
        if (userOptional.isEmpty()) {
            message = MessageUtil.getMessage(messageSource, "user.id.not-found");
            buildContext(message, context);
            return false;
        }

        // Kiểm tra email nếu thay đổi
        if (!userOptional.get().getEmail().equals(record.email())) {
            Optional<User> emailOptional = userRepository.findByEmail(record.email());
            if (emailOptional.isPresent()) {
                message = MessageUtil.getMessage(messageSource, "user.email.exists");
                buildContext(message, context);
                return false;
            }
        }

        // Kiểm tra UUID chỉ khi ID thay đổi
        if (!userOptional.get().getId().equals(record.id())) {
            Optional<User> uuidOptional = userRepository.findById(record.id());
            if (uuidOptional.isPresent()) {
                message = MessageUtil.getMessage(messageSource, "user.uuid.exists");
                buildContext(message, context);
                return false;
            }
        }

        return true;
    }
}

