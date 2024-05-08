package org.website.thienan.ricewaterthienan.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.website.thienan.ricewaterthienan.entities.Product;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProductService {

    Product save(Product product);

    Product update(Product product);

    Optional<Product> findById(String id);

    void deleteById(String id);

    Page<Product> findByActive(Pageable pageable, Boolean active);

    Page<Product> findAllFilter(Pageable pageable, String name,
                          Double price,
                          Long views,
                          Boolean active,
                          LocalDateTime create);


}
