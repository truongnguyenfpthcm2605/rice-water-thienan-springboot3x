package org.website.thienan.ricewaterthienan.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "type_post",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    Set<Post> posts = new HashSet<>();

}
