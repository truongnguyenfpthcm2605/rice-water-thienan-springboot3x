package org.website.thienan.ricewaterthienan.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriesResponse extends  BaseResponse {
    Integer id;
    String name;
    String link;
    String introduction;
    String content;
    String avatar;
    String imageHeader;
    AccountResponse accountResponse;
    List<PostResponse> postResponses;
}
