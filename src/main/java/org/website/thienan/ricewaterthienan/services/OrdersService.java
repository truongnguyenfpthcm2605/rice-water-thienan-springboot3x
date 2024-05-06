package org.website.thienan.ricewaterthienan.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrdersService<T,R>{
    T save(R orders);
    T update(R orders);
    Optional<T> findById(String id);
    Optional<T> findByName(String name);
    Optional<T> findByPhone(String phone);
    void deleteById(String id);
    Page<T> findByKeyword(Pageable pageable, String keyword);
    Page<T> findByActive(Pageable pageable,Boolean active);

    Page<T> findByStatus(Pageable pageable, String status);


}
