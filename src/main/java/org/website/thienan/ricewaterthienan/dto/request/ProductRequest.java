package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
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
    String accountId;
    Integer branchId;
    Integer brandId;
    Set<String> categories = new HashSet<>();
}
