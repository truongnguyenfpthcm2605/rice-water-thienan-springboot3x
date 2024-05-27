package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoriesPostRequest extends BaseRequest {
    @NotBlank
    @Size(min = 10, max = 100)
    String name;
    @NotBlank
    String link;
    Long views;
    String accountId;

}
