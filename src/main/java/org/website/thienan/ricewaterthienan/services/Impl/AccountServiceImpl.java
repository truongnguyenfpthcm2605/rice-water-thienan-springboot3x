package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.repositories.RoleDetailRepository;
import org.website.thienan.ricewaterthienan.services.AccountServices;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountServices {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleDetailRepository roleDetailRepository;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "accounts", allEntries = true),
                    @CacheEvict(cacheNames = "accountsActive", allEntries = true)
            }
    )
    public Account save(AccountRequest accountRequest) {
        return accountRepository.save(Account.builder()
                .name(accountRequest.getName())
                .password(passwordEncoder.encode(accountRequest.getPassword()))
                .email(accountRequest.getEmail())
                .avatar(accountRequest.getAvatar())
                .role(accountRequest.getRoleEnum())
                .active(Boolean.TRUE)
                .views(1L)
                .roles(accountRequest.getRoleDetail().stream().map(e -> roleDetailRepository.findByName(e)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Role Name"))).collect(Collectors.toSet()))
                .build());
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
    public Account update(AccountRequest accountRequest) {
        return accountRepository.save(Account.builder()
                .name(accountRequest.getName())
                .password(passwordEncoder.encode(accountRequest.getPassword()))
                .email(accountRequest.getEmail())
                .avatar(accountRequest.getAvatar())
                .role(accountRequest.getRoleEnum())
                .active(accountRequest.getActive())
                .views(accountRequest.getViews())
                .roles(accountRequest.getRoleDetail().stream().map(e -> roleDetailRepository.findByName(e)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Role Name"))).collect(Collectors.toSet()))
                .build());

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
