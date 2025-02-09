package com.example.CRM.user.controller;


import com.example.CRM.common.model.PageReponsese;
import com.example.CRM.common.reponsese.ApiReponsese;
import com.example.CRM.common.util.DateTimeUtil;
import com.example.CRM.common.validate.group.InsertInfo;
import com.example.CRM.common.validate.group.UpdateInfo;
import com.example.CRM.security.util.AuthorizeUtil;
import com.example.CRM.user.model.record.UserRecord;
import com.example.CRM.user.model.reponsese.UserDTO;
import com.example.CRM.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/User")
@AllArgsConstructor
@Slf4j
@Validated
public class UserRestController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Validated(InsertInfo.class) @RequestBody UserRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(record));

    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Validated(UpdateInfo.class) @RequestBody UserRecord record) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(record.id(),record));
    }

    @GetMapping
    @PreAuthorize(AuthorizeUtil.ADMIN)
    @SecurityRequirement(name = "bearer-key")
    public ApiReponsese<PageReponsese<UserDTO>> getUsers(
            @RequestParam int pageSize,
            @RequestParam int page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        // Xác định thứ tự sắp xếp
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(Sort.Direction.DESC, sortBy)
                : Sort.by(Sort.Direction.ASC, sortBy);

        // Tạo Pageable object
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        // Gọi service với phân trang
        PageReponsese<UserDTO> usersPage = userService.findAllByPage(pageable);

        // Trả về danh sách UserDTO (hoặc thông tin phân trang nếu cần)
        return ApiReponsese.<PageReponsese<UserDTO>>builder()
                .result(usersPage)
                .timestamp(DateTimeUtil.now())
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
