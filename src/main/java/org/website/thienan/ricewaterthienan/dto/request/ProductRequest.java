package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest extends BaseRequest {
    @NotBlank
    String name;
    @NotBlank
    String link;
    @PositiveOrZero
    @NotBlank
    Double price;
    @PositiveOrZero
    @NotBlank
    Double cost;
    @NotBlank
    String avatar;
    @NotBlank
    String description;
    Long views;
    @NotBlank
    String content;
    @NotBlank
    String accountId;
    @NotNull
    Integer branchId;
    @NotNull
    Integer brandId;
    @NotEmpty
    Set<String> categories = new HashSet<>();
}
