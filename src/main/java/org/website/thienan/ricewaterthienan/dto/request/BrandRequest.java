package org.website.thienan.ricewaterthienan.dto.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest extends BaseRequest {
    @NotBlank
    @Max(value = 15)
    String name;
    @NotBlank
    String avatar;
    @NotNull
    Long views;
    @NotBlank
    String accountId;
}
