package org.website.thienan.ricewaterthienan.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.website.thienan.ricewaterthienan.dto.response.BaseResponse;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryPostRequest  extends BaseResponse {
    String name;
    String link;
    Long views;
    String accountId;

}
