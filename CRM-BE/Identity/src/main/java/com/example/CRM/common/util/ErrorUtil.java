package com.example.CRM.common.util;

import com.example.CRM.common.exception.AppException;
import com.example.CRM.user.user.model.User;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.xml.bind.ValidationException;
import lombok.experimental.UtilityClass;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class ErrorUtil {

    public static List<String> getErrorMessage(ValidationException exception){
        return List.of(exception.getMessage());
    }

    public static List<String> getErrorMessage(MethodArgumentNotValidException exception){
        return exception.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
    }

    public static List<String> getErrorMessage(InvalidFormatException exception){
        Class<?> targetType = exception.getTargetType();
        if (UUID.class.equals(targetType)) {
            return List.of(MessageUtil.INVALID_UUID_FORMAT);
        } else if (LocalDate.class.equals(targetType)){
            return List.of(MessageUtil.INVALID_DATE_FORMAT);
        } else if (User.StatusVerified.class.equals(targetType)){
            return List.of(MessageUtil.INVALID_USER_ACCOUNT_STATUS);

//        } else if (Project.Status.class.equals(targetType)){
//            return List.of(MessageUtil.INVALID_PROJECT_STATUS);
//        } else if (Task.Status.class.equals(targetType)){
//            return List.of(MessageUtil.INVALID_TASK_STATUS);
//        } else if (Notification.Status.class.equals(targetType)){
//            return List.of(MessageUtil.INVALID_NOTIFICATION_STATUS);
        } else {
            return List.of(exception.getOriginalMessage());
        }
    }

    //ConstraintViolationException is a child of ValidationException -> it catches exception before its parents
    public static List<String> getErrorMessage(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
    }

    public static List<String> getErrorMessage(AppException exception) {
        return List.of(exception.getMessage());
    }

    public static List<String> getErrorMessage(Exception exception) {

        if (exception instanceof MethodArgumentNotValidException e) {
            // Lấy tất cả các lỗi từ FieldError và trả về thông báo chi tiết
            return e.getBindingResult().getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }

        return List.of(exception.getMessage());
    }

}
