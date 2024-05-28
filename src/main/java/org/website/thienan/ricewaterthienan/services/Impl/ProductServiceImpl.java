package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.elasticsearch.documments.ProductDocument;
import org.website.thienan.ricewaterthienan.elasticsearch.repositories.ProductSearchRepository;
import org.website.thienan.ricewaterthienan.elasticsearch.services.ProductSearchService;
import org.website.thienan.ricewaterthienan.entities.Product;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.ProductRepository;
import org.website.thienan.ricewaterthienan.services.ProductService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductSearchService productSearchService;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void synchronizeDataSearch() {
        List<Product> products = productRepository.findAll();
        productSearchService.saveAll(products);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "products", allEntries = true),
                    @CacheEvict(cacheNames = "productFilter", allEntries = true)
            }
    )
    public Product save(Product product) {
        Product saved = productRepository.save(product);
        productSearchService.saveProductSearch(saved);
        return  productRepository.save(product);
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
    public Product update(Product product) {
        Product saved = productRepository.save(product);
        productSearchService.updateProductSearch(saved);
        return  productRepository.save(product);

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
        productSearchService.deleteByIdProductSearch(id);
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

    @Override
    public Page<ProductDocument> search(Pageable pageable, String name, Double price, Long views, Boolean active, LocalDateTime create) {
        return productSearchService.search(pageable, name, price,price+100000,create, views, active);
    }


}
