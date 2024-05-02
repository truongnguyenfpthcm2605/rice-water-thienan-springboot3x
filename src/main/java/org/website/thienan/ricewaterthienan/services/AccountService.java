package org.website.thienan.ricewaterthienan.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.dto.response.AccountResponse;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountResponse save(AccountRequest account);
    AccountResponse update(AccountRequest account);
    Optional<AccountResponse> findById(String id);
    void delete(String id);
    Page<AccountResponse> findAll(Pageable pageable,Boolean active);
    Optional<AccountResponse> findByEmailAndActive(String email,Boolean active);
    List<AccountResponse> findByActive(boolean active);
}
