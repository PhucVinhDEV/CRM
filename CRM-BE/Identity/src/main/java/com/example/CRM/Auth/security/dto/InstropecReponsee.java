package com.example.CRM.Auth.security.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public class InstropecReponsee {
    boolean valid;
    Date expiration;
}
