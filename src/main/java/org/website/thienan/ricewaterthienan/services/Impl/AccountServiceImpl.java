package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.dto.response.AccountResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.AccountMapper;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.services.AccountService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class AccountServiceImpl implements AccountService<AccountRequest,AccountResponse> {

    private  final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "accounts", allEntries = true),
                    @CacheEvict(cacheNames = "accountsActive", allEntries = true)
            }
    )
    public AccountResponse save(AccountRequest account) {
        Account acc = accountMapper.account(account);
        return accountMapper.accountResponse(accountRepository.save(acc));
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "account", key = "#AccountRequest.id")

            },
            evict = {
                    @CacheEvict(cacheNames = "accounts", allEntries = true),
                    @CacheEvict(cacheNames = "accountsActive", allEntries = true)
            }
    )
    public AccountResponse update(AccountRequest account) {
        Account acc = accountMapper.account(account);
        return accountMapper.accountResponse(accountRepository.saveAndFlush(acc));
    }

    @Override
    @Cacheable(cacheNames = "account",key = "#id", unless = "#result == null")
    public Optional<AccountResponse> findById(String id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Account NotFound"));
        return Optional.of(accountMapper.accountResponse(account));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "account", key="#id", allEntries = true),
                    @CacheEvict(cacheNames = "accounts", allEntries = true),
                    @CacheEvict(cacheNames = "accountsActive", allEntries = true)
            }
    )
    public void delete(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "accountsActive")
    public Page<AccountResponse> findAll(Pageable pageable, Boolean active) {
        Page<Account> accountPage = accountRepository.findByActive(pageable, active);

        List<AccountResponse> accountResponses = accountPage.getContent().stream()
                .map(account -> {
                    AccountResponse accountResponse = accountMapper.accountResponse(account);
                    return accountResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(accountResponses, pageable, accountPage.getTotalElements());
    }

    @Override
    @Cacheable(cacheNames  = "accountByEmail", unless = "#result==null", key = "#email")
    public Optional<AccountResponse> findByEmailAndActive(String email, Boolean active) {
        Account account = accountRepository.findByEmailAndActive(email,active).orElseThrow(() -> new ResourceNotFoundException("Account Email Not Found"));
        return Optional.of(accountMapper.accountResponse(account));
    }

    @Override
    @Cacheable(cacheNames = "accounts")
    public List<AccountResponse> findAll() {
        return accountRepository.findAll().stream().map(account ->  {
            AccountResponse accountResponse = accountMapper.accountResponse(account);
            return accountResponse;
        }).collect(Collectors.toList());
    }
}
