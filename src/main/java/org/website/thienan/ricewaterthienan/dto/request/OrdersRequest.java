package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.website.thienan.ricewaterthienan.exceptions.customValidation.PhoneNumbers;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersRequest extends BaseRequest {
    @PhoneNumbers
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
    @NotBlank
    String status;
    @NotBlank
    String accountId;
}
