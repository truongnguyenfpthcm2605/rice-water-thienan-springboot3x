package org.website.thienan.ricewaterthienan.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.website.thienan.ricewaterthienan.exceptions.customValidation.PasswordRegex;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
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
    @NotBlank
    String role;
    @NotNull
    Long views;
    @NotEmpty
    Set<String> roleDetail = new HashSet<>();
}
