package org.website.thienan.ricewaterthienan.services.Impl;

import java.util.Optional;

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
import org.website.thienan.ricewaterthienan.entities.Orders;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.OrdersRepository;
import org.website.thienan.ricewaterthienan.services.OrdersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;

    @Override
    @Caching(
            evict = {
                @CacheEvict(cacheNames = "ordersKeyWord", allEntries = true),
                @CacheEvict(cacheNames = "ordersActive", allEntries = true),
                @CacheEvict(cacheNames = "ordersStatus", allEntries = true)
            })
    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Override
    @Caching(
            put = {@CachePut(cacheNames = "orders", key = "#OrdersRequest.id")},
            evict = {
                @CacheEvict(cacheNames = "ordersKeyWord", allEntries = true),
                @CacheEvict(cacheNames = "ordersActive", allEntries = true),
                @CacheEvict(cacheNames = "ordersStatus", allEntries = true)
            })
    public Orders update(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Override
    @Cacheable(cacheNames = "orders", key = "#id", unless = "#result==null")
    public Optional<Orders> findById(String id) {
        Orders orders = ordersRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Orders ID : " + id));
        return Optional.of(orders);
    }

    @Override
    @Cacheable(cacheNames = "orders", key = "#name", unless = "#result==null")
    public Optional<Orders> findByName(String name) {
        Orders orders = ordersRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Orders NAME : " + name));
        return Optional.of(orders);
    }

    @Override
    @Cacheable(cacheNames = "orders", key = "#phone", unless = "#result==null")
    public Optional<Orders> findByPhone(String phone) {
        Orders orders = ordersRepository
                .findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Orders PHONE : " + phone));
        return Optional.of(orders);
    }

    @Override
    @Caching(
            evict = {
                @CacheEvict(cacheNames = "orders", key = "#id"),
                @CacheEvict(cacheNames = "ordersKeyWord", allEntries = true),
                @CacheEvict(cacheNames = "ordersActive", allEntries = true),
                @CacheEvict(cacheNames = "ordersStatus", allEntries = true)
            })
    public void deleteById(String id) {
        ordersRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "ordersKeyWord")
    public Page<Orders> findByKeyword(Pageable pageable, String keyword) {
        return ordersRepository.findByKeyword(pageable, "%" + keyword + "%");
    }

    @Override
    @Cacheable(cacheNames = "ordersActive")
    public Page<Orders> findByActive(Pageable pageable, Boolean active) {
        return ordersRepository.findByActive(pageable, active);
    }

    @Override
    @Cacheable(cacheNames = "ordersStatus")
    public Page<Orders> findByStatus(Pageable pageable, String status) {
        return ordersRepository.findByStatus(pageable, status);
    }
}
