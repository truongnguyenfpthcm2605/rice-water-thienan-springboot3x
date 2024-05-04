package org.website.thienan.ricewaterthienan.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeResponse extends BaseResponse {
    Integer id;
    String title;
    String link;
    String content;
    String introduction;
    String avatar;
    String imageHeader;
    Long views;
    Set<PostResponse> postResponses = new HashSet<>();
    AccountResponse accountResponse;
}
