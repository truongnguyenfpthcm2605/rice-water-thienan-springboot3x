package org.website.thienan.ricewaterthienan.dto.request;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest extends  BaseRequest{
    Integer id;
    String name;
    String avatar;
    Long views;
    String accountId;
}
