package org.website.thienan.ricewaterthienan.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.website.thienan.ricewaterthienan.messages.MessageValidation;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoriesRequest extends  BaseRequest {
    @NotBlank(message = MessageValidation.NAME_CATEGORIES_MESSAGE)
    String name;
    @NotBlank(message = MessageValidation.LINK_CATEGORIES_MESSAGE)
    String link;
    @NotBlank(message = MessageValidation.CONTENT_CATEGORIES_MESSAGE)
    String content;
    @NotBlank(message = MessageValidation.INTRODUCTION_CATEGORIES_MESSAGE)
    String introduction;
    @NotBlank(message = MessageValidation.AVATAR_CATEGORIES_MESSAGE)
    String avatar;
    @NotBlank(message = MessageValidation.IMAGE_HEADER_CATEGORIES_MESSAGE)
    String imageHeader;
    String accountId;
    Long views;


}
