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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoriesRequest extends  BaseRequest {
    String name;
    String link;
    String content;
    String introduction;
    String avatar;
    String imageHeader;
    String accountId;
    Set<Integer> categoryPost = new HashSet<>();

}
