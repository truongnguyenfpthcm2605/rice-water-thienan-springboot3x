package org.website.thienan.ricewaterthienan.elasticsearch.documments;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.HashSet;
import java.util.Set;
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "products")
@SuperBuilder
public class ProductDocument extends BaseSearch {
    @Id
    String id;
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
