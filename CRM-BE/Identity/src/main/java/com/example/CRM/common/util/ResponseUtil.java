package com.example.CRM.common.util;

import com.example.CRM.common.reponsese.ApiReponsese;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@UtilityClass
public class ResponseUtil {

    private <T> ResponseEntity<ApiReponsese<T>> buildResponse(T result, boolean hasErrors, List<String> errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(
                ApiReponsese.<T>builder()
                        .result(result)
                        .timestamp(DateTimeUtil.now())
                        .hasErrors(hasErrors)
                        .errors(errorMessage)
                        .status(httpStatus.value())
                        .build(),
                httpStatus
        );
    }

    public  ResponseEntity<ApiReponsese<Void>> error(Exception e,HttpStatus httpStatus){
        e.printStackTrace();
        return buildResponse(null,true,ErrorUtil.getErrorMessage(e),httpStatus);
    }

    public ResponseEntity<ApiReponsese<Object>> success(HttpStatus httpStatus, Object result){
        return  buildResponse(result,false,null,httpStatus);
    }

//    public static ResponseEntity<ApiReponsese> error(ValidationException e, HttpStatus httpStatus) {
//        return  new ResponseEntity<>(
//                ApiReponsese.builder()
//                        .result(null)
//                        .timestamp(DateTimeUtil.now())
//                        .hasErrors(true)
//                        .errors(ErrorUtil.getErrorMessage(e))
//                        .status(httpStatus.value())
//                        .build()
//                , httpStatus
//        );
//    }
//
//    public static ResponseEntity<ApiReponsese> error(MethodArgumentNotValidException e, HttpStatus httpStatus) {
//        return  new ResponseEntity<>(
//                ApiReponsese.builder()
//                        .result(null)
//                        .timestamp(DateTimeUtil.now())
//                        .hasErrors(true)
//                        .errors(ErrorUtil.getErrorMessage(e))
//                        .status(httpStatus.value())
//                        .build()
//                , httpStatus
//        );
//    }
//    public static ResponseEntity<ApiReponsese> error(InvalidFormatException e, HttpStatus httpStatus) {
//        return  new ResponseEntity<>(
//                ApiReponsese.builder()
//                        .result(null)
//                        .timestamp(DateTimeUtil.now())
//                        .hasErrors(true)
//                        .errors(ErrorUtil.getErrorMessage(e))
//                        .status(httpStatus.value())
//                        .build()
//                , httpStatus
//        );
//    }
//
//    public static ResponseEntity<ApiReponsese> error(ConstraintViolationException e, HttpStatus httpStatus) {
//        return  new ResponseEntity<>(
//                ApiReponsese.builder()
//                        .result(null)
//                        .timestamp(DateTimeUtil.now())
//                        .hasErrors(true)
//                        .errors(ErrorUtil.getErrorMessage(e))
//                        .status(httpStatus.value())
//                        .build()
//                , httpStatus
//        );
//    }
//    public static ResponseEntity<ApiReponsese> error(AppException e, HttpStatus httpStatus) {
//        return  new ResponseEntity<>(
//                ApiReponsese.builder()
//                        .result(null)
//                        .timestamp(DateTimeUtil.now())
//                        .hasErrors(true)
//                        .errors(ErrorUtil.getErrorMessage(e))
//                        .status(httpStatus.value())
//                        .build()
//                , httpStatus
//        );
//    }
//
//    public static ResponseEntity<ApiReponsese<Object>> success(AppException e, HttpStatus httpStatus,Object dto) {
//        return  new ResponseEntity<>(
//                ApiReponsese.builder()
//                        .result(dto)
//                        .timestamp(DateTimeUtil.now())
//                        .hasErrors(true)
//                        .errors(ErrorUtil.getErrorMessage(e))
//                        .status(httpStatus.value())
//                        .build()
//                , httpStatus
//        );
//    }
}
