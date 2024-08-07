package org.website.thienan.ricewaterthienan.services;

import java.util.List;
import java.util.Optional;

import org.website.thienan.ricewaterthienan.entities.Categories;

public interface CategoriesService {
    Categories save(Categories Categories);

    Categories update(Categories Categories);

    Optional<Categories> findById(Integer id);

    Optional<Categories> findByName(String name);

    void deleteById(Integer id);

    List<Categories> findAll();

    List<Categories> findByActive(Boolean active);
}
