package org.website.thienan.ricewaterthienan.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.website.thienan.ricewaterthienan.entities.Type;

public interface TypeService {
    Type save(Type postRequest);

    Type update(Type postRequest);

    Optional<Type> findById(Integer id);

    void deleteById(Integer id);

    Page<Type> findByActive(Pageable pageable, Boolean active);

    Page<Type> findByTitle(Pageable pageable, String title, boolean Active);
}
