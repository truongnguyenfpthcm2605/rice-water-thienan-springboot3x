package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.CategoriesPostRequest;
import org.website.thienan.ricewaterthienan.entities.CategoriesPost;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.repositories.CategoriesPostRepository;
import org.website.thienan.ricewaterthienan.services.CategoriesPostService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriesPostServiceImpl implements CategoriesPostService {
    private final CategoriesPostRepository categoriesPostRepository;
    private final AccountRepository accountRepository;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "categoriesPosts", allEntries = true),
                    @CacheEvict(cacheNames = "categoriesPostActive", allEntries = true)
            }
    )
    public CategoriesPost save(CategoriesPostRequest categoriesRequest) {
        return categoriesPostRepository.save(CategoriesPost.builder()
                .name(categoriesRequest.getName())
                .link(categoriesRequest.getLink())
                .views(1L)
                .active(Boolean.TRUE)
                .account(accountRepository.findById(categoriesRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")))
                .build());
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
    public CategoriesPost update(CategoriesPostRequest categoriesRequest) {
        return categoriesPostRepository.save(CategoriesPost.builder()
                .name(categoriesRequest.getName())
                .link(categoriesRequest.getLink())
                .views(categoriesRequest.getViews())
                .active(categoriesRequest.getActive())
                .account(accountRepository.findById(categoriesRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")))
                .build());
    }

    @Override
    @Cacheable(cacheNames = "categoryPost", key = "#id", unless = "#result==null")
    public Optional<CategoriesPost> findById(Integer id) {
        CategoriesPost categories = categoriesPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found CategoriesPost ID : " + id));
        return Optional.of(categories);
    }

    @Override
    @Cacheable(cacheNames = "categoryPost", key = "#name", unless = "#result==null")
    public Optional<CategoriesPost> findByName(String name) {
        CategoriesPost categories = categoriesPostRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found CategoriesPost Name : " + name));
        return Optional.of(categories);
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
    public List<CategoriesPost> findAll() {
        return categoriesPostRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "categoriesPostActive")
    public List<CategoriesPost> findByActive(Boolean active) {
        return categoriesPostRepository.findByActive(active);
    }
}

