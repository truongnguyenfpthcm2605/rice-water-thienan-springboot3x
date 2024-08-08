package org.website.thienan.ricewaterthienan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "tbl_brand")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand extends  BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 100, nullable = false, unique = true)
    String name;

    @Column(length = 999)
    String avatar;

    @Column(columnDefinition= "BIGINT", nullable = false)
    Long views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonBackReference
    Account account;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Product> products;
}
