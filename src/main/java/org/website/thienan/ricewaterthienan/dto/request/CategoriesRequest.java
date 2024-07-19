package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoriesRequest extends BaseRequest {
    @NotBlank
    @Size(min = 10, max = 100)
    String name;

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

    @NotBlank
    String accountId;

    @NotNull
    Long views;
}
