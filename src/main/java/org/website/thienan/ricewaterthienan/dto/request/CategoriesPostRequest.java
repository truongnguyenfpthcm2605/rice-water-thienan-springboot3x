package org.website.thienan.ricewaterthienan.dto.request;

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
    Integer id;
    String name;
    String link;
    Long views;
    String accountId;

}
