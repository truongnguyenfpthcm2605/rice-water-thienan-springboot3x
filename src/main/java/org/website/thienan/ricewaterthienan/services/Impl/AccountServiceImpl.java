package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.dto.response.AccountResponse;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.services.AccountService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class AccountServiceImpl implements AccountService {

    private  final AccountRepository accountRepository;
    @Override
    @CacheEvict(cacheNames = "accounts", allEntries = true)
    public AccountResponse save(AccountRequest account) {
        return null;
    }

    @Override
    @CachePut(cacheNames = "account", key = "#AccountRequest.id")
    @CacheEvict(cacheNames = "accounts", allEntries = true)
    public AccountResponse update(AccountRequest account) {
        return null;
    }

    @Override
    @Cacheable(cacheNames = "account",key = "#id", unless = "#result == null")
    public Optional<AccountResponse> findById(String id) {
        return Optional.empty();
    }

    @Override
    @CacheEvict(value = "account", key="#id", allEntries = true)
    public void delete(String id) {
            accountRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "accounts")
    public Page<AccountResponse> findAll(Pageable pageable, Boolean active) {
        return null;
    }

    @Override
    @Cacheable(cacheNames  = "accountByEmail", unless = "#result==null", key = "#email")
    public Optional<AccountResponse> findByEmailAndActive(String email, Boolean active) {
        return Optional.empty();
    }

    @Override
    @Cacheable(cacheNames = "accountActive")
    public List<AccountResponse> findByActive(boolean active) {
        return null;
    }
}
