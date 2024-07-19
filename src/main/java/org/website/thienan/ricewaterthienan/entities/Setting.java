package org.website.thienan.ricewaterthienan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "setting")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Setting extends BaseEntity {
    @Id
    String id;

    @Column(columnDefinition = "TINYTEXT")
    String custom;
}
