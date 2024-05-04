package org.website.thienan.ricewaterthienan.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface AccountService<R,T> {
    T save(R account);
    T update(R account);
    Optional<T> findById(String id);
    void delete(String id);
    Page<T> findAll(Pageable pageable,Boolean active);
    Optional<T> findByEmailAndActive(String email,Boolean active);
    List<T> findAll();
}
