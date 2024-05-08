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
import org.website.thienan.ricewaterthienan.entities.Brand;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.BrandRepository;
import org.website.thienan.ricewaterthienan.services.BrandService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class BrandServiceImpl  implements BrandService {

    private final BrandRepository brandRepository;
    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "brands", allEntries = true),
                    @CacheEvict(cacheNames = "brandsActive", allEntries = true)
            }
    )
    public Brand save(Brand branchRequest) {
       return brandRepository.save(branchRequest);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "brand", key="#BrandRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "brands", allEntries = true),
                    @CacheEvict(cacheNames = "brandsActive", allEntries = true)
            }
    )
    public Brand update(Brand branchRequest) {
        return brandRepository.save(branchRequest);
    }

    @Override
    @Cacheable(cacheNames = "brand", key = "#id", unless = "#result==null")
    public Optional<Brand> findById(Integer id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Brand Id: "+ id ));
        return Optional.of(brand);
    }

    @Override
    @Cacheable(cacheNames = "brand", key = "#name", unless = "#result==null")
    public Optional<Brand> findByName(String name) {
        Brand brand = brandRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found Brand Id: "+ name ));
        return Optional.of(brand);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "brand", key = "#id"),
                    @CacheEvict(cacheNames = "brands", allEntries = true),
                    @CacheEvict(cacheNames = "brandsActive", allEntries = true)
            }
    )
    public void deleteById(Integer id) {
        brandRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "brands")
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "brandsActive")
    public List<Brand> findByActive(Boolean active) {
        return brandRepository.findByActive(active);
    }
}
