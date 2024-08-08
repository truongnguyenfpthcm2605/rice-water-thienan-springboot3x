package org.website.thienan.ricewaterthienan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;
import org.website.thienan.ricewaterthienan.enums.NotificationEnum;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "tbl_notification")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String message;

    @Enumerated(EnumType.STRING)
    @NaturalId
    NotificationEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonBackReference
    Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonBackReference
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    @JsonBackReference
    Orders order;

}
