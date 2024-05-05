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
public class ProductRequest {
    String name;
    String link;
    Double price;
    Double cost;
    String avatar;
    String description;
    Long views;
    String content;
    String accountId;
    Integer branchId;
    Integer brandId;
    Set<String> categories = new HashSet<>();
}
