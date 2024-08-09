package org.website.thienan.ricewaterthienan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity implements Serializable {

    @Column(name = "create_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date updateAt;

    Boolean active;

}
