package org.website.thienan.ricewaterthienan.services.Impl;

import java.util.Optional;

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
import org.website.thienan.ricewaterthienan.entities.Type;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.TypeRepository;
import org.website.thienan.ricewaterthienan.services.TypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;

    @Override
    @Caching(
            evict = {
                @CacheEvict(cacheNames = "typeActive", allEntries = true),
                @CacheEvict(cacheNames = "typeTitle", allEntries = true),
            })
    public Type save(Type postRequest) {
        return typeRepository.save(postRequest);
    }

    @Override
    @Caching(
            put = {@CachePut(cacheNames = "type", key = "#TypeRequest.id")},
            evict = {
                @CacheEvict(cacheNames = "typeActive", allEntries = true),
                @CacheEvict(cacheNames = "typeTitle", allEntries = true),
            })
    public Type update(Type postRequest) {
        return typeRepository.save(postRequest);
    }

    @Override
    @Cacheable(cacheNames = "type", key = "#id", unless = "#result==null")
    public Optional<Type> findById(Integer id) {
        Type type = typeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type not found with id " + id));
        return Optional.of(type);
    }

    @Override
    @Caching(
            evict = {
                @CacheEvict(cacheNames = "type", key = "#id"),
                @CacheEvict(cacheNames = "typeActive", allEntries = true),
                @CacheEvict(cacheNames = "typeTitle", allEntries = true),
            })
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
