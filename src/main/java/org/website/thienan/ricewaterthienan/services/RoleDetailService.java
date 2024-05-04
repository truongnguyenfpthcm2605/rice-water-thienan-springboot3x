package org.website.thienan.ricewaterthienan.services;


import java.util.List;
import java.util.Optional;

public interface RoleDetailService<R,T> {
    T save(R roleDetail);
    T update(R roleDetail);
    Optional<T> findById(Integer id);
    Optional<T> findByName(String name);
    void deleteById(Integer id);
    List<T> findAll();
    List<T> findByActive(Boolean active);

}
