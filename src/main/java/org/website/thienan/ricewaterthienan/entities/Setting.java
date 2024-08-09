package org.website.thienan.ricewaterthienan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "tbl_setting")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Setting extends BaseEntity {
    @Id
    String id;

    @Column(length = 512, nullable = false)
    String custom;


}
