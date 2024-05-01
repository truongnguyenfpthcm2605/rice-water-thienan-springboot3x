package org.website.thienan.ricewaterthienan.enums;

import lombok.Getter;

@Getter
public enum ValidationMessageEnum {
    USERNAME_EMPTY(2000, "Username is not empty"),
    PASSWORD_EMPTY(2001, "Password is not empty");

    ValidationMessageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;
}
