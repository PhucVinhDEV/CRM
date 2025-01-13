package com.example.CRM.CustomerEAV.util;

import lombok.Getter;


@Getter
public enum TypeOfValue {
    STRING("String"),
    NUMBER("Number"),
    DATE("Date");

    private final String displayName;

    TypeOfValue(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
