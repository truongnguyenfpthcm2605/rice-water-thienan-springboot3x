package org.website.thienan.ricewaterthienan.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseRequest implements Serializable {
    LocalDateTime createAt;
    LocalDateTime updateAt;
    Boolean active;
}
