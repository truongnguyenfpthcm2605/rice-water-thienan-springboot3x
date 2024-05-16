package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


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
