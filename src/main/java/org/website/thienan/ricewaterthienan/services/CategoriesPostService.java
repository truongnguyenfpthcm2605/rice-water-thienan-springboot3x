package org.website.thienan.ricewaterthienan.services;

import org.website.thienan.ricewaterthienan.dto.request.CategoriesPostRequest;
import org.website.thienan.ricewaterthienan.entities.CategoriesPost;

import java.util.List;
import java.util.Optional;

public interface CategoriesPostService {
    CategoriesPost save(CategoriesPostRequest branchRequest);
    CategoriesPost update(CategoriesPostRequest branchRequest);
    Optional<CategoriesPost> findById(Integer id);
    Optional<CategoriesPost> findByName(String name);
    void deleteById(Integer id);
    List<CategoriesPost> findAll();
    List<CategoriesPost> findByActive(Boolean active);
}
