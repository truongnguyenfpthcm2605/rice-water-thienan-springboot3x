package org.website.thienan.ricewaterthienan.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseRequest implements Serializable {
    LocalDateTime createAt;
    LocalDateTime updateAt;
    Boolean active;
}
