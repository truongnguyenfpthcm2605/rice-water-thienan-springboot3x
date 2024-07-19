package org.website.thienan.ricewaterthienan.dto.request;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeRequest extends BaseRequest {
    @NotBlank
    @Size(min = 10, max = 100)
    String title;

    @NotBlank
    String link;

    @NotBlank
    String content;

    @NotBlank
    String introduction;

    @NotBlank
    String avatar;

    @NotBlank
    String imageHeader;

    Long views;

    @NotEmpty
    Set<Integer> typePost = new HashSet<>();

    @NotBlank
    String accountId;
}
