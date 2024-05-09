package org.website.thienan.ricewaterthienan.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.website.thienan.ricewaterthienan.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountServices {
    Account save(Account account);
    Account update(Account account);
    Optional<Account> findById(String id);
    void deleteById(String id);
    Page<Account> findAll(Pageable pageable,Boolean active);
    Optional<Account> findByEmailAndActive(String email,Boolean active);
    List<Account> findAll();
}
