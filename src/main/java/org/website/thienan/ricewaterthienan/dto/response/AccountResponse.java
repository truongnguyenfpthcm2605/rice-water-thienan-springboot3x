package org.website.thienan.ricewaterthienan.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    String email;
    String name;
    String token;
    String refreshToken;
    String timeToken;
    LocalDateTime time;
    String avatar;
    private Collection<? extends GrantedAuthority> authorities;
}
