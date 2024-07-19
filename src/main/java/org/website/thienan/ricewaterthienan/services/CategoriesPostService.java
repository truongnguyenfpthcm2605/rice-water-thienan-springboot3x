package org.website.thienan.ricewaterthienan.services;

import java.util.List;
import java.util.Optional;

import org.website.thienan.ricewaterthienan.entities.CategoriesPost;

public interface CategoriesPostService {
    CategoriesPost save(CategoriesPost branchRequest);

    CategoriesPost update(CategoriesPost branchRequest);

    Optional<CategoriesPost> findById(Integer id);

    Optional<CategoriesPost> findByName(String name);

    void deleteById(Integer id);

    List<CategoriesPost> findAll();

    List<CategoriesPost> findByActive(Boolean active);
}
