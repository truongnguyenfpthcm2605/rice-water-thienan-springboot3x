package org.website.thienan.ricewaterthienan.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SettingRequest extends BaseRequest {
    @NotBlank
    String id;
    @NotBlank
    String custom;
}
