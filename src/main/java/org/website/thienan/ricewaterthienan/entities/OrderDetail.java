package org.website.thienan.ricewaterthienan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity(name = "tbl_order_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    Double quantity;

    @Column(nullable = false)
    Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonBackReference
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    @JsonBackReference
    Orders order;
}
