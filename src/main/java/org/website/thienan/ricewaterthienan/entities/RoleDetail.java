package org.website.thienan.ricewaterthienan.entities;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role_detail")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 100, nullable = false)
    String name;
}
