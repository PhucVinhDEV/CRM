package com.example.CRM.Auth.security.util;


public class AuthorizeUtil {
     public static final  String ROOT = "hasAnyAuthority('ROLE_ADMIN','ROLE_ROOT','ROLE_APPLIER')";
     public static final   String EMPLOYER = "hasAnyAuthority('ROLE_ADMIN','ROLE_EMPLOYER')";
     public static final   String APPLIER = "hasAnyAuthority('ROLE_ADMIN','ROLE_APPLIER')";
     public static final   String ADMIN = "hasAnyAuthority('ROLE_ADMIN')";
     public static final   String NONE = "permitAll()";
}
