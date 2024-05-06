package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.CategoriesRequest;
import org.website.thienan.ricewaterthienan.dto.response.CategoriesResponse;
import org.website.thienan.ricewaterthienan.entities.Categories;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.CategoriesMapper;
import org.website.thienan.ricewaterthienan.repositories.CategoriesRepository;
import org.website.thienan.ricewaterthienan.services.CategoriesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class CategoriesServiceImpl implements CategoriesService<CategoriesResponse, CategoriesRequest> {
    private final CategoriesRepository categoriesRepository;
    private final CategoriesMapper categoriesMapper;
    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "categories", allEntries = true),
                    @CacheEvict(cacheNames = "categoriesActive", allEntries = true)
            }
    )
    public CategoriesResponse save(CategoriesRequest categoriesRequest) {
        Categories categories = categoriesMapper.toCategories(categoriesRequest);
        return categoriesMapper.categoriesResponse(categoriesRepository.save(categories));
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "category", key = "#CategoriesRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "categories", allEntries = true),
                    @CacheEvict(cacheNames = "categoriesActive", allEntries = true)
            }
    )
    public CategoriesResponse update(CategoriesRequest categoriesRequest) {
        Categories categories = categoriesMapper.toCategories(categoriesRequest);
        return categoriesMapper.categoriesResponse(categoriesRepository.saveAndFlush(categories));
    }

    @Override
    @Cacheable(cacheNames = "category", key="#id", unless = "#result==null")
    public Optional<CategoriesResponse> findById(Integer id) {
        Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Categories ID : " + id));
        return Optional.of(categoriesMapper.categoriesResponse(categories));
    }

    @Override
    @Cacheable(cacheNames = "category", key="#name", unless = "#result==null")
    public Optional<CategoriesResponse> findByName(String name) {
        Categories categories = categoriesRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found Categories Name : " + name));
        return Optional.of(categoriesMapper.categoriesResponse(categories));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "category", allEntries = true),
                    @CacheEvict(cacheNames = "categories", allEntries = true),
                    @CacheEvict(cacheNames = "categoriesActive", allEntries = true)
            }
    )
    public void deleteById(Integer id) {
        categoriesRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "categories")
    public List<CategoriesResponse> findAll() {
        return categoriesRepository.findAll().stream().map( category -> {
            CategoriesResponse categoriesResponse = categoriesMapper.categoriesResponse(category);
            return categoriesResponse;
        }).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "categoriesActive")
    public List<CategoriesResponse> findByActive(Boolean active) {
        return categoriesRepository.findByActive(active).stream().map( category -> {
            CategoriesResponse categoriesResponse = categoriesMapper.categoriesResponse(category);
            return categoriesResponse;
        }).collect(Collectors.toList());
    }
}
