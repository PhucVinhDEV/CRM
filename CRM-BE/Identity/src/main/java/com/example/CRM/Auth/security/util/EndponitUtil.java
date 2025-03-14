package com.example.CRM.Auth.security.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EndponitUtil {

    String[] REDIS_SECURITY_ENDPOINTS = {
            "/api/auth/login",
            "/api/auth/refresh",
            "/api/user/secure-action",
            "/change-password",
            "/api/auth/outbound",
    };

    String[] PUBLIC_GET_ENDPOINTS = {};
    String[] PRIVATE_GET_ENDPOINTS = {};
    public boolean isRedisSecuredEndpoint(String requestURI) {
        for (String endpoint : REDIS_SECURITY_ENDPOINTS) {
            if (requestURI.startsWith(endpoint)) {
                return true;
            }
        }
        return false;
    }
}
