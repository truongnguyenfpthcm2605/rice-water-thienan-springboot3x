package org.website.thienan.ricewaterthienan.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.website.thienan.ricewaterthienan.dto.request.BranchRequest;
import org.website.thienan.ricewaterthienan.dto.request.BrandRequest;
import org.website.thienan.ricewaterthienan.entities.*;

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
public class AccountResponse extends BaseResponse {
    String id;
    String name;
    String password;
    String email;
    String avatar;
    Long views;
    String role;
    Set<RoleDetailResponse> roleDetails = new HashSet<>();
    List<BranchRequest> branches;
    List<BrandRequest> brands;
    List<Product> products;
    List<Orders> orders;
    List<Categories> categories;
    List<Post> posts;
    List<Type> types;
    List<CategoriesPost> categoriesPosts;

}