package org.website.thienan.ricewaterthienan.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse extends  BaseResponse {
    String id;
    String name;
    String link;
    Double price;
    Double cost;
    String avatar;
    String content;
    String description;
    Long views;
    AccountResponse accountResponse;
    BranchResponse branchResponse;
    BrandResponse brandResponse;
    Set<CategoriesResponse> categoriesResponseSet = new HashSet<>();
    List<OrderdetailResponse> orderdetailResponses;

}
