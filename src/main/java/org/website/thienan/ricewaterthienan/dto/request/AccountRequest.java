package org.website.thienan.ricewaterthienan.dto.request;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.*;

import org.website.thienan.ricewaterthienan.enums.RoleEnum;
import org.website.thienan.ricewaterthienan.exceptions.customValidation.EnumPattern;
import org.website.thienan.ricewaterthienan.exceptions.customValidation.PasswordRegex;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequest extends BaseRequest {
    @NotBlank
    @Size(min = 10, max = 100)
    String name;

    @PasswordRegex
    String password;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String avatar;

    @EnumPattern(name = "Role", regexp = "ADMIN|USER|STAFF")
    RoleEnum roleEnum;

    @NotNull
    Long views;

    @NotEmpty
    Set<String> roleDetail = new HashSet<>();
}
