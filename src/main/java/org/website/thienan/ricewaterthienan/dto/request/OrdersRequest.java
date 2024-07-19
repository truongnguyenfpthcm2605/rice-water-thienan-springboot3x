package org.website.thienan.ricewaterthienan.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import org.website.thienan.ricewaterthienan.enums.StatusOrderEnum;
import org.website.thienan.ricewaterthienan.exceptions.customValidation.PhoneNumbers;
import org.website.thienan.ricewaterthienan.exceptions.customValidation.StatusSubnet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
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

    @StatusSubnet(
            anyOf = {
                StatusOrderEnum.CANCEL,
                StatusOrderEnum.DELIVERY,
                StatusOrderEnum.COMPLETED,
                StatusOrderEnum.COMPLETED
            })
    StatusOrderEnum status;

    @NotBlank
    String accountId;
}
