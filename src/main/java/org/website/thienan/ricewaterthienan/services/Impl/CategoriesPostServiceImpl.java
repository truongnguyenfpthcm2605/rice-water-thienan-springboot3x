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
import org.website.thienan.ricewaterthienan.dto.request.CategoriesPostRequest;
import org.website.thienan.ricewaterthienan.dto.response.CategoriesPostResponse;
import org.website.thienan.ricewaterthienan.dto.response.CategoriesResponse;
import org.website.thienan.ricewaterthienan.entities.Categories;
import org.website.thienan.ricewaterthienan.entities.CategoriesPost;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.CategoriesPostMapper;
import org.website.thienan.ricewaterthienan.repositories.CategoriesPostRepository;
import org.website.thienan.ricewaterthienan.services.CategoriesPostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class CategoriesPostServiceImpl implements CategoriesPostService<CategoriesPostResponse, CategoriesPostRequest> {
    private final CategoriesPostRepository categoriesPostRepository;
    private final CategoriesPostMapper categoriesMapper;
    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "categoriesPosts", allEntries = true),
                    @CacheEvict(cacheNames = "categoriesPostActive", allEntries = true)
            }
    )
    public CategoriesPostResponse save(CategoriesPostRequest categoriesRequest) {
        CategoriesPost categories = categoriesMapper.categoriesPost(categoriesRequest);
        return categoriesMapper.categoriesPostResponse(categoriesPostRepository.save(categories));
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "categoryPost", key = "#CategoriesRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "categoriesPosts", allEntries = true),
                    @CacheEvict(cacheNames = "categoriesPostActive", allEntries = true)
            }
    )
    public CategoriesPostResponse update(CategoriesPostRequest categoriesRequest) {
        CategoriesPost categories = categoriesMapper.categoriesPost(categoriesRequest);
        return categoriesMapper.categoriesPostResponse(categoriesPostRepository.saveAndFlush(categories));
    }

    @Override
    @Cacheable(cacheNames = "categoryPost", key="#id", unless = "#result==null")
    public Optional<CategoriesPostResponse> findById(Integer id) {
        CategoriesPost categories = categoriesPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found CategoriesPost ID : " + id));
        return Optional.of(categoriesMapper.categoriesPostResponse(categories));
    }

    @Override
    @Cacheable(cacheNames = "categoryPost", key="#name", unless = "#result==null")
    public Optional<CategoriesPostResponse> findByName(String name) {
        CategoriesPost categories = categoriesPostRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found CategoriesPost Name : " + name));
        return Optional.of(categoriesMapper.categoriesPostResponse(categories));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "categoryPost", allEntries = true),
                    @CacheEvict(cacheNames = "categoriesPosts", allEntries = true),
                    @CacheEvict(cacheNames = "categoriesPostActive", allEntries = true)
            }
    )
    public void deleteById(Integer id) {
        categoriesPostRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "categoriesPosts")
    public List<CategoriesPostResponse> findAll() {
        return categoriesPostRepository.findAll().stream().map( category -> {
            CategoriesPostResponse categoriesResponse = categoriesMapper.categoriesPostResponse(category);
            return categoriesResponse;
        }).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "categoriesPostActive")
    public List<CategoriesPostResponse> findByActive(Boolean active) {
        return categoriesPostRepository.findByActive(active).stream().map( category -> {
            CategoriesPostResponse categoriesResponse = categoriesMapper.categoriesPostResponse(category);
            return categoriesResponse;
        }).collect(Collectors.toList());
    }
}

