package org.website.thienan.ricewaterthienan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "type")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Type extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 100, nullable = false)
    String title;

    String link;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    String introduction;

    @Column(columnDefinition = "TEXT", nullable = false)
    String content;

    @Column(length = 999)
    String avatar;

    @Column(length = 999, name = "imageheader")
    String imageHeader;

    @Column(columnDefinition= "BIGINT", nullable = false)
    Long views;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "type_post",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    Set<Post> posts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonBackReference
    Account account;

}
