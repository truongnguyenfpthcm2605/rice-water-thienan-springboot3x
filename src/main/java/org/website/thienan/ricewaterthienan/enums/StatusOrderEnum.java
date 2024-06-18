package org.website.thienan.ricewaterthienan.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StatusOrderEnum {
    @JsonProperty("Confirm")
    CONFIRM,
    @JsonProperty("Delivery")
    DELIVERY,
    @JsonProperty("Completed")
    COMPLETED,
    @JsonProperty("Cancel")
    CANCEL


}
