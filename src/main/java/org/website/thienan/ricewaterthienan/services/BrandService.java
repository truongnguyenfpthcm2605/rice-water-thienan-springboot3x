package org.website.thienan.ricewaterthienan.services;

import org.website.thienan.ricewaterthienan.dto.request.BrandRequest;
import org.website.thienan.ricewaterthienan.entities.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Brand save(BrandRequest branchRequest);
    Brand update(BrandRequest branchRequest);
    Optional<Brand> findById(Integer id);
    Optional<Brand> findByName(String name);
    void deleteById(Integer id);
    List<Brand> findAll();
    List<Brand> findByActive(Boolean active);

}
