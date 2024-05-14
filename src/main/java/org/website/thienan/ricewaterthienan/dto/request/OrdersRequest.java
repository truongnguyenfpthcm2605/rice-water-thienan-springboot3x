package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersRequest extends BaseRequest {
    @NotBlank
    @Max(value = 15)
    String phone;
    @NotBlank
    @Max(value = 100)
    String name;
    @NotBlank
    @Max(value = 256)
    String address;
    @Max(value = 256)
    @NotBlank
    String notes;
    String status;
    String accountId;
}
