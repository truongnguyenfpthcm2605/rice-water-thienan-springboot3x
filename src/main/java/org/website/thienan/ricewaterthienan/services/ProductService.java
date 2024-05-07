package org.website.thienan.ricewaterthienan.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProductService<T, R> {

    T save(R product);

    T update(R product);

    Optional<T> findById(String id);

    void deleteById(String id);

    Page<T> findByActive(Pageable pageable, Boolean active);

    Page<T> findAllFilter(Pageable pageable, String name,
                          Double price,
                          Long views,
                          Boolean active,
                          LocalDateTime create);


}
