package org.website.thienan.ricewaterthienan.entities;

import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categories_post")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoriesPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 100, nullable = false, unique = true)
    String name;

    String link;

    @Column(columnDefinition = "BIGINT", nullable = false)
    Long views;

    @OneToMany(mappedBy = "categoryPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonBackReference
    Account account;
}
