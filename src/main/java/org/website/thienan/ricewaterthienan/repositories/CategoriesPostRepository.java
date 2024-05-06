package org.website.thienan.ricewaterthienan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.CategoriesPost;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesPostRepository extends JpaRepository<CategoriesPost, Integer> {

    Optional<CategoriesPost> findByName(String name);
    List<CategoriesPost> findByActive(Boolean active);
}
