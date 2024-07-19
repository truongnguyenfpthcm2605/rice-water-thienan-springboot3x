package org.website.thienan.ricewaterthienan.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersDetailRequest implements Serializable {
    @NotNull
    @PositiveOrZero
    Double quantity;

    @NotNull
    @PositiveOrZero
    Double price;

    @NotBlank
    String productId;

    String orderId;
}
