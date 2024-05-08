package org.website.thienan.ricewaterthienan.services;

import org.website.thienan.ricewaterthienan.entities.Branch;
import org.website.thienan.ricewaterthienan.entities.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Brand save(Brand branchRequest);
    Brand update(Brand branchRequest);
    Optional<Brand> findById(Integer id);
    Optional<Brand> findByName(String name);
    void deleteById(Integer id);
    List<Brand> findAll();
    List<Brand> findByActive(Boolean active);

}
