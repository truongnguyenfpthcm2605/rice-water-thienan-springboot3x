package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.CategoriesRequest;
import org.website.thienan.ricewaterthienan.entities.Categories;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.repositories.CategoriesRepository;
import org.website.thienan.ricewaterthienan.services.CategoriesService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriesServiceImpl implements CategoriesService{
    private final CategoriesRepository categoriesRepository;
    private final AccountRepository accountRepository;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "categories", allEntries = true),
                    @CacheEvict(cacheNames = "categoriesActive", allEntries = true)
            }
    )
    public Categories save(CategoriesRequest categoriesRequest) {
        return categoriesRepository.save(Categories.builder()
                .name(categoriesRequest.getName())
                .link(categoriesRequest.getLink())
                .introduction(categoriesRequest.getIntroduction())
                .content(categoriesRequest.getContent())
                .avatar(categoriesRequest.getAvatar())
                .imageHeader(categoriesRequest.getImageHeader())
                .views(1L)
                .active(Boolean.TRUE)
                .account(accountRepository.findById(categoriesRequest.getAccountId()).orElseThrow(()-> new ResourceNotFoundException("Not found Account")))
                .build());
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
    public Categories update(CategoriesRequest categoriesRequest) {

        return  categoriesRepository.save(Categories.builder()
                .name(categoriesRequest.getName())
                .link(categoriesRequest.getLink())
                .introduction(categoriesRequest.getIntroduction())
                .content(categoriesRequest.getContent())
                .avatar(categoriesRequest.getAvatar())
                .imageHeader(categoriesRequest.getImageHeader())
                .views(categoriesRequest.getViews())
                .active(categoriesRequest.getActive())
                .account(accountRepository.findById(categoriesRequest.getAccountId()).orElseThrow(()-> new ResourceNotFoundException("Not found Account")))
                .build());
    }

    @Override
    @Cacheable(cacheNames = "category", key="#id", unless = "#result==null")
    public Optional<Categories> findById(Integer id) {
        Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Categories ID : " + id));
        return Optional.of(categories);
    }

    @Override
    @Cacheable(cacheNames = "category", key="#name", unless = "#result==null")
    public Optional<Categories> findByName(String name) {
        Categories categories = categoriesRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found Categories Name : " + name));
        return Optional.of(categories);
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
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "categoriesActive")
    public List<Categories> findByActive(Boolean active) {
        return categoriesRepository.findByActive(active);
    }
}
