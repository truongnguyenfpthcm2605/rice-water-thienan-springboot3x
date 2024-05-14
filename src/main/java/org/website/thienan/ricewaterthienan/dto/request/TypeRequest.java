package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class TypeRequest extends BaseRequest {
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
    Set<Integer> typePost = new HashSet<>();
    String accountId;

}
