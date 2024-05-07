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
import org.website.thienan.ricewaterthienan.dto.request.ProductRequest;
import org.website.thienan.ricewaterthienan.dto.response.ProductResponse;
import org.website.thienan.ricewaterthienan.entities.Product;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.ProductMapper;
import org.website.thienan.ricewaterthienan.repositories.ProductRepository;
import org.website.thienan.ricewaterthienan.services.ProductService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService<ProductResponse, ProductRequest> {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "products", allEntries = true),
                    @CacheEvict(cacheNames = "productFilter", allEntries = true)
            }
    )
    public ProductResponse save(ProductRequest product) {
        Product product1 = productMapper.product(product);
        return productMapper.productResponse(productRepository.save(product1));
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "product", key="#ProductRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "products", allEntries = true),
                    @CacheEvict(cacheNames = "productFilter", allEntries = true)
            }
    )
    public ProductResponse update(ProductRequest product) {
        Product product1 = productMapper.product(product);
        return productMapper.productResponse(productRepository.saveAndFlush(product1));
    }

    @Override
    @Cacheable(cacheNames = "product", key = "#name", unless = "#result==null")
    public Optional<ProductResponse> findById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found Product ID : " + id));
        return Optional.of(productMapper.productResponse(product));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "product", key = "#id"),
                    @CacheEvict(cacheNames = "products", allEntries = true),
                    @CacheEvict(cacheNames = "productFilter", allEntries = true)
            }
    )
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "products")
    public Page<ProductResponse> findByActive(Pageable pageable, Boolean active) {
        Page<Product> productPage = productRepository.findByActive(pageable, active);

        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(product -> {
                    ProductResponse productResponse = productMapper.productResponse(product);
                    return productResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(productResponses, pageable, productPage.getTotalElements());
    }

    @Override
    @Cacheable(cacheNames = "productFilter")
    public Page<ProductResponse> findAllFilter(Pageable pageable, String name, Double price, Long views, Boolean active, LocalDateTime create) {
        Page<Product> productPage = productRepository
                .findAllFilter(pageable, name, price, views, active, create);

        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(product -> {
                    ProductResponse productResponse = productMapper.productResponse(product);
                    return productResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(productResponses, pageable, productPage.getTotalElements());
    }
}
