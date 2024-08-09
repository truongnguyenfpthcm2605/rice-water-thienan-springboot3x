package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.TypeRequest;
import org.website.thienan.ricewaterthienan.entities.Type;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.repositories.PostRepository;
import org.website.thienan.ricewaterthienan.repositories.TypeRepository;
import org.website.thienan.ricewaterthienan.services.TypeService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "typeActive", allEntries = true),
                    @CacheEvict(cacheNames = "typeTitle", allEntries = true),
            }
    )
    public Type save(TypeRequest typeRequest) {

        return typeRepository.save(Type.builder()
                .title(typeRequest.getTitle())
                .link(typeRequest.getLink())
                .introduction(typeRequest.getIntroduction())
                .content(typeRequest.getContent())
                .avatar(typeRequest.getAvatar())
                .imageHeader(typeRequest.getImageHeader())
                .views(1L)
                .active(Boolean.TRUE)
                .posts(typeRequest.getTypePost().stream().map(e -> postRepository.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not found Category"))).collect(Collectors.toSet()))
                .account(accountRepository.findById(typeRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")))
                .build());
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "type", key = "#TypeRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "typeActive", allEntries = true),
                    @CacheEvict(cacheNames = "typeTitle", allEntries = true),
            }
    )
    public Type update(TypeRequest typeRequest) {
        return typeRepository.save(Type.builder()
                .title(typeRequest.getTitle())
                .link(typeRequest.getLink())
                .introduction(typeRequest.getIntroduction())
                .content(typeRequest.getContent())
                .avatar(typeRequest.getAvatar())
                .imageHeader(typeRequest.getImageHeader())
                .views(typeRequest.getViews())
                .active(typeRequest.getActive())
                .posts(typeRequest.getTypePost().stream().map(e -> postRepository.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not found Category"))).collect(Collectors.toSet()))
                .account(accountRepository.findById(typeRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")))
                .build());
    }

    @Override
    @Cacheable(cacheNames = "type", key = "#id", unless = "#result==null")
    public Optional<Type> findById(Integer id) {
        Type type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Type not found with id " + id));
        return Optional.of(type);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "type", key = "#id"),
                    @CacheEvict(cacheNames = "typeActive", allEntries = true),
                    @CacheEvict(cacheNames = "typeTitle", allEntries = true),
            }
    )
    public void deleteById(Integer id) {
        typeRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "typeActive")
    public Page<Type> findByActive(Pageable pageable, Boolean active) {
        return typeRepository.findByActive(pageable, active);

    }

    @Override
    @Cacheable(cacheNames = "typeTitle")
    public Page<Type> findByTitle(Pageable pageable, String title, boolean Active) {
        return typeRepository.findByTitle(pageable, "%" + title + "%", Active);

    }
}
