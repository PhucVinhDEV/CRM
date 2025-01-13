package com.example.CRM.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JoinTableUtil {
    //USER & ROLE
    public static final String USER_MAPPED_BY_ROLE = "ROLE_ID";

    //ROLE & PERMISSION
    public static final String ROLE_AND_PERMISSION = "ROLE_PERMISSION";
    public static final String ROLE_ID = "ROLE_ID";
    public static final String PERMISSION_ID = "PERMISSION_ID";

    //EAV Customer
    public static final String EAV_MAPPING_CUSTOMER = "CUSTOMER_ID";
    public static final String EAV_MAPPING_ATTRIBUTE = "attributeId";
}
