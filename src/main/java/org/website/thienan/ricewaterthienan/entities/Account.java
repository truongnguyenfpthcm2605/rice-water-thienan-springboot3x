package org.website.thienan.ricewaterthienan.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;
import org.website.thienan.ricewaterthienan.enums.RoleEnum;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(length = 100, nullable = false)
    String name;

    @Column(length = 512, nullable = false)
    String password;

    @Column(length = 100, nullable = false, unique = true)
    String email;

    @Column(length = 999)
    String avatar;


    @Enumerated(EnumType.STRING)
    @NaturalId
    RoleEnum role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "authorities",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "roledetail_id")
    )
    Set<RoleDetail> roles = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Branch> branch;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Brand> brands;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Product> products ;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Orders> orders;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Categories> categories;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Post> posts;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Type> types;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    List<CategoriesPost> categoriesPosts;




}
