package org.website.thienan.ricewaterthienan.services;

import java.util.List;
import java.util.Optional;

public interface BrandService<T,R> {
    T save(R branchRequest);
    T update(R branchRequest);
    Optional<T> findById(Integer id);
    Optional<T> findByName(String name);
    void deleteById(Integer id);
    List<T> findAll();
    List<T> findByActive(Boolean active);

}
