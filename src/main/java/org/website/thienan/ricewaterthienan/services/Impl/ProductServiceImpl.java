package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
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
import org.website.thienan.ricewaterthienan.entities.Product;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.ProductRepository;
import org.website.thienan.ricewaterthienan.services.ProductService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "products", allEntries = true),
                    @CacheEvict(cacheNames = "productFilter", allEntries = true)
            }
    )
    public Product save(Product product) {
        Product saved = productRepository.save(product);
        return productRepository.save(product);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "product", key = "#ProductRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "products", allEntries = true),
                    @CacheEvict(cacheNames = "productFilter", allEntries = true)
            }
    )
    public Product update(Product product) {
        Product saved = productRepository.save(product);
        return productRepository.save(product);

    }

    @Override
    @Cacheable(cacheNames = "product", key = "#name", unless = "#result==null")
    public Optional<Product> findById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found Product ID : " + id));
        return Optional.of(product);
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
    public Page<Product> findByActive(Pageable pageable, Boolean active) {
        return productRepository.findByActive(pageable, active);

    }

    @Override
    @Cacheable(cacheNames = "productFilter")
    public Page<Product> findAllFilter(Pageable pageable, String name, Double price, Long views, Boolean active, LocalDateTime create) {
        return productRepository
                .findAllFilter(pageable, name, price, views, active, create);

    }


}
