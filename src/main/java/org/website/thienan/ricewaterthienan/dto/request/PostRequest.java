package org.website.thienan.ricewaterthienan.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequest {
    String title;
    String link;
    String content;
    String introduction;
    String avatar;
    String imageHeader;
    Long views;
    String categoriesPostId;
    String accountId;

    Set<Integer> categoryPost = new HashSet<>();

}
