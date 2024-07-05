package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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
    @NotNull
    Long views;
    @NotNull
    Integer categoriesPostId;
    @NotBlank
    String accountId;
    @NotEmpty
    Set<Integer> categories = new HashSet<>();

}
