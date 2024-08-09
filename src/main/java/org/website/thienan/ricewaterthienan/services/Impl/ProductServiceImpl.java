package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.ProductRequest;
import org.website.thienan.ricewaterthienan.entities.Product;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.*;
import org.website.thienan.ricewaterthienan.security.AccountService;
import org.website.thienan.ricewaterthienan.services.ProductService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final BrandRepository brandRepository;
    private final BranchRepository branchRepository;
    private final CategoriesRepository categoriesRepository;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "products", allEntries = true),
                    @CacheEvict(cacheNames = "productFilter", allEntries = true)
            }
    )
    public Product save(ProductRequest productRequest) {
        return productRepository.save(Product.builder()
                .name(productRequest.getName())
                .link(productRequest.getLink())
                .price(productRequest.getPrice())
                .content(productRequest.getContent())
                .cost(productRequest.getCost())
                .avatar(productRequest.getAvatar())
                .description(productRequest.getDescription())
                .views(1L)
                .active(Boolean.TRUE)
                .account(accountRepository.findById(productRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")))
                .brand(brandRepository.findById(productRequest.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Not found Brand")))
                .branch(branchRepository.findById(productRequest.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Not found Branch")))
                .categories(productRequest.getCategories().stream().map(e -> categoriesRepository.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not found Category"))).collect(Collectors.toSet()))
                .build());
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
    public Product update(ProductRequest productRequest) {
        return productRepository.save(Product.builder()
                .name(productRequest.getName())
                .link(productRequest.getLink())
                .price(productRequest.getPrice())
                .content(productRequest.getContent())
                .cost(productRequest.getCost())
                .avatar(productRequest.getAvatar())
                .description(productRequest.getDescription())
                .views(productRequest.getViews())
                .active(productRequest.getActive())
                .account(accountRepository.findById(productRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")))
                .brand(brandRepository.findById(productRequest.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Not found Brand")))
                .branch(branchRepository.findById(productRequest.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Not found Branch")))
                .categories(productRequest.getCategories().stream().map(e -> categoriesRepository.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not found Category"))).collect(Collectors.toSet()))
                .build());

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
