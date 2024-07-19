package org.website.thienan.ricewaterthienan.dto.response;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
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
