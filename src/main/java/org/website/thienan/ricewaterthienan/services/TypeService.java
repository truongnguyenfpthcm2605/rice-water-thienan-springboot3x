package org.website.thienan.ricewaterthienan.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.website.thienan.ricewaterthienan.dto.request.TypeRequest;
import org.website.thienan.ricewaterthienan.entities.Type;

import java.util.Optional;

public interface TypeService {
    Type save(TypeRequest postRequest);
    Type update(TypeRequest postRequest);
    Optional<Type> findById(Integer id);
    void deleteById(Integer id);
    Page<Type> findByActive(Pageable pageable, Boolean active);
    Page<Type> findByTitle( Pageable pageable, String title, boolean Active);
}
