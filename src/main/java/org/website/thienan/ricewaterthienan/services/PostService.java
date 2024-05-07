package org.website.thienan.ricewaterthienan.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostService<T,R>{
    T save(R postRequest);
    T update(R postRequest);
    Optional<T> findById(Integer id);
    void deleteById(Integer id);
    Page<T> findByActive(Pageable pageable, Boolean active);
    Page<T> findByTitle( Pageable pageable, String title, boolean Active);
}
