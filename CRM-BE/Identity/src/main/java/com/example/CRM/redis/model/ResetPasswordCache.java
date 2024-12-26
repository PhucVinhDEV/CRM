package com.example.CRM.redis.model;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResetPasswordCache {
    String token;
    Timestamp expiryTime;
    UUID UserId;
}
