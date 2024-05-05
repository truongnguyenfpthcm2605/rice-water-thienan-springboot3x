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
import org.website.thienan.ricewaterthienan.dto.request.BrandRequest;
import org.website.thienan.ricewaterthienan.dto.response.BaseResponse;
import org.website.thienan.ricewaterthienan.dto.response.BrandResponse;
import org.website.thienan.ricewaterthienan.entities.Brand;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.BrandMapper;
import org.website.thienan.ricewaterthienan.repositories.BrandRepository;
import org.website.thienan.ricewaterthienan.services.BrandService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class BrandServiceImpl  implements BrandService<BrandResponse, BrandRequest> {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "brands", allEntries = true),
                    @CacheEvict(cacheNames = "brandsActive", allEntries = true)
            }
    )
    public BrandResponse save(BrandRequest branchRequest) {
        Brand brand = brandMapper.brand(branchRequest);
        return brandMapper.brandResponse(brandRepository.save(brand));
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
    public BrandResponse update(BrandRequest branchRequest) {
        Brand brand = brandMapper.brand(branchRequest);
        return brandMapper.brandResponse(brandRepository.saveAndFlush(brand));
    }

    @Override
    @Cacheable(cacheNames = "brand", key = "#id", unless = "#result==null")
    public Optional<BrandResponse> findById(Integer id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Brand Id: "+ id ));
        return Optional.of(brandMapper.brandResponse(brand));
    }

    @Override
    @Cacheable(cacheNames = "brand", key = "#name", unless = "#result==null")
    public Optional<BrandResponse> findByName(String name) {
        Brand brand = brandRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found Brand Id: "+ name ));
        return Optional.of(brandMapper.brandResponse(brand));
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
    public List<BrandResponse> findAll() {
        return brandRepository.findAll().stream().map( brand -> {
            BrandResponse baseResponse = brandMapper.brandResponse(brand);
            return  baseResponse;
        }).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "brandsActive")
    public List<BrandResponse> findByActive(Boolean active) {
        return brandRepository.findByActive(active).stream().map( brand -> {
            BrandResponse baseResponse = brandMapper.brandResponse(brand);
            return  baseResponse;
        }).collect(Collectors.toList());
    }
}
