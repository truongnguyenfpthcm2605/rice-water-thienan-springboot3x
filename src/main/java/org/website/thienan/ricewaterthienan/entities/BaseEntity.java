package org.website.thienan.ricewaterthienan.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseEntity implements Serializable {

    @Column(name = "create_at")
    @CreatedDate
    LocalDateTime createAt;

    @Column(name = "update_at")
    @LastModifiedDate
    LocalDateTime updateAt;

    Boolean active;
}
