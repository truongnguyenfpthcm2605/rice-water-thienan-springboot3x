package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequest extends BaseRequest {
    @NotBlank
    @Size(min = 10, max = 100)
    String title;
    @NotBlank
    String link;
    @NotBlank
    String content;
    @NotBlank
    String introduction;
    @NotBlank
    String avatar;
    @NotBlank
    String imageHeader;
    Long views;
    Integer categoriesPostId;
    String accountId;

    Set<Integer> categories = new HashSet<>();

}
