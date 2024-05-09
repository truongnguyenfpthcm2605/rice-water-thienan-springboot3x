package org.website.thienan.ricewaterthienan.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.website.thienan.ricewaterthienan.messages.MessageValidation;

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
    String id;
    String name;
    @NotBlank(message = MessageValidation.PASSWORD_MESSAGE)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$", message = MessageValidation.PASSWORD_PATTERN)
    String password;
    @NotBlank(message = MessageValidation.EMAIL_MESSAGE)
    @Email
    String email;
    String avatar;
    @NotBlank(message = MessageValidation.ROLE_MESSAGE)
    String role;
    Long views;
    @NotEmpty(message = MessageValidation.ROLE_DETAIL_MESSAGE)
    Set<String> roleDetail = new HashSet<>();
}
