package org.website.thienan.ricewaterthienan.services;

import java.util.List;
import java.util.Optional;

import org.website.thienan.ricewaterthienan.entities.Brand;

public interface BrandService {
    Brand save(Brand branchRequest);

    Brand update(Brand branchRequest);

    Optional<Brand> findById(Integer id);

    Optional<Brand> findByName(String name);

    void deleteById(Integer id);

    List<Brand> findAll();

    List<Brand> findByActive(Boolean active);
}
