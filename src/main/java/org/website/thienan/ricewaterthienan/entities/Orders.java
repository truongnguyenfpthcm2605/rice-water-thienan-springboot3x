package org.website.thienan.ricewaterthienan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;
import org.website.thienan.ricewaterthienan.enums.StatusOrderEnum;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(length = 15, nullable = false)
    String phone;

    @Column(length = 100, nullable = false)
    String name;

    @Column(nullable = false)
    String address;

    String notes;

    @Enumerated(EnumType.STRING)
    @NaturalId
    StatusOrderEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonBackReference
    Account account;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    List<OrderDetail> orderDetails;


}
