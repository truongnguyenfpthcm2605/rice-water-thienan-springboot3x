package org.website.thienan.ricewaterthienan.enums;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum MessagesHanlderEnum {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    FILE_EXCEPTION(1009, "Exception File", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    MAIL_EXCEPTION(1010, "Mail Exception ", HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_EXIT(1011, "Resource Exit Error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOTFOUND(1012, "The Data not Found", HttpStatus.NOT_FOUND),
    PERMISSION(1013, "Access Denied", HttpStatus.UNAUTHORIZED),
    SQL_EXCEPTION(1014, "SQL ERROR INJECTION", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    MessagesHanlderEnum(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.statusCode = httpStatus;
    }
}
