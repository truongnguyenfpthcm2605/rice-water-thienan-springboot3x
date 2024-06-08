package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.services.AccountServices;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class AccountServiceImpl implements AccountServices {

    private final AccountRepository accountRepository;


    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "accounts", allEntries = true),
                    @CacheEvict(cacheNames = "accountsActive", allEntries = true)
            }
    )
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "account", key = "#Account.id")

            },
            evict = {
                    @CacheEvict(cacheNames = "accounts", allEntries = true),
                    @CacheEvict(cacheNames = "accountsActive", allEntries = true)
            }
    )
    public Account update(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    @Cacheable(cacheNames = "account", key = "#id", unless = "#result == null")
    public Optional<Account> findById(String id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account NotFound"));
        return Optional.of(account);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "account", key = "#id", allEntries = true),
                    @CacheEvict(cacheNames = "accounts", allEntries = true),
                    @CacheEvict(cacheNames = "accountsActive", allEntries = true)
            }
    )
    public void deleteById(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "accountsActive")
    public Page<Account> findAll(Pageable pageable, Boolean active) {
        return accountRepository.findByActive(pageable, active);
    }

    @Override
    @Cacheable(cacheNames = "accountByEmail", unless = "#result==null", key = "#email")
    public Optional<Account> findByEmailAndActive(String email, Boolean active) {
        Account account = accountRepository.findByEmailAndActive(email, active).orElseThrow(() -> new ResourceNotFoundException("Account Email Not Found"));
        return Optional.of(account);
    }

    @Override
    @Cacheable(cacheNames = "accounts")
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }
}
