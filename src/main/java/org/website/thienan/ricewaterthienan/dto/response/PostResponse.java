package org.website.thienan.ricewaterthienan.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class PostResponse extends BaseResponse{
    Integer id;
    String title;
    String link;
    String content;
    String introduction;
    String avatar;
    String imageHeader;
    Long views;
    AccountResponse accountResponse;
    CategoriesPostResponse categoriesPostResponse;
}
