package org.website.thienan.ricewaterthienan.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.website.thienan.ricewaterthienan.enums.RoleEnum;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest extends BaseRequest {
    String name;
    String password;
    String email;
    String avatar;
    String role;
    Set<String> roleDetail = new HashSet<>();
}
