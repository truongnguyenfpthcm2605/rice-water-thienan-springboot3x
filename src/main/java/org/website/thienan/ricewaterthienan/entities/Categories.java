package org.website.thienan.ricewaterthienan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Categories extends  BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 100,nullable = false)
    String name;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonBackReference
    Account account;



}
