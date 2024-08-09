package org.website.thienan.ricewaterthienan.services;

import org.website.thienan.ricewaterthienan.dto.request.CategoriesRequest;
import org.website.thienan.ricewaterthienan.entities.Categories;

import java.util.List;
import java.util.Optional;

public interface CategoriesService {
    Categories save(CategoriesRequest Categories);
    Categories update(CategoriesRequest Categories);
    Optional<Categories> findById(Integer id);
    Optional<Categories> findByName(String name);
    void deleteById(Integer id);
    List<Categories> findAll();
    List<Categories> findByActive(Boolean active);
}
