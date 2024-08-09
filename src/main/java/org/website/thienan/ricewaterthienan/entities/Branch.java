package org.website.thienan.ricewaterthienan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "tbl_branch")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Branch extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 100, nullable = false, unique = true)
    String name;

    @Column(columnDefinition= "BIGINT", nullable = false)
    Long views;

    String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonBackReference
    Account account;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonManagedReference
    List<Product> products;
}
