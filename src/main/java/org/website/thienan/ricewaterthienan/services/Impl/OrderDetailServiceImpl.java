package org.website.thienan.ricewaterthienan.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.entities.OrderDetail;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.OrderDetailRepository;
import org.website.thienan.ricewaterthienan.services.OrderDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Caching(
            evict = {
                @CacheEvict(cacheNames = "orderDetails", allEntries = true),
                @CacheEvict(cacheNames = "ordersDetailProduct", allEntries = true),
                @CacheEvict(cacheNames = "ordersDetailOrders", allEntries = true)
            })
    public List<OrderDetail> saveAll(List<OrderDetail> list) {
        return orderDetailRepository.saveAllAndFlush(list);
    }

    @Override
    @Caching(
            evict = {
                @CacheEvict(cacheNames = "orderDetails", allEntries = true),
                @CacheEvict(cacheNames = "ordersDetailProduct", allEntries = true),
                @CacheEvict(cacheNames = "ordersDetailOrders", allEntries = true)
            })
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    @Caching(
            put = {@CachePut(cacheNames = "orderDetail", key = "#OrderDetail.id")},
            evict = {
                @CacheEvict(cacheNames = "orderDetails", allEntries = true),
                @CacheEvict(cacheNames = "ordersDetailProduct", allEntries = true),
                @CacheEvict(cacheNames = "ordersDetailOrders", allEntries = true)
            })
    public OrderDetail update(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    @Cacheable(cacheNames = "orderDetail", key = "#id", unless = "#result==null")
    public Optional<OrderDetail> findById(Integer id) {
        OrderDetail orderDetail = orderDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found OrderDetail Id : " + id));
        return Optional.of(orderDetail);
    }

    @Override
    @Caching(
            evict = {
                @CacheEvict(cacheNames = "orderDetail", key = "#id"),
                @CacheEvict(cacheNames = "orderDetails", allEntries = true),
                @CacheEvict(cacheNames = "ordersDetailProduct", allEntries = true),
                @CacheEvict(cacheNames = "ordersDetailOrders", allEntries = true)
            })
    public void deleteById(Integer id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "orderDetails")
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "ordersDetailProduct")
    public List<OrderDetail> findByProductId(String productId) {
        return orderDetailRepository.findByProductId(productId);
    }

    @Override
    @Cacheable(cacheNames = "ordersDetailOrders")
    public List<OrderDetail> findByOrdersId(String ordersId) {
        return orderDetailRepository.findByOrdersId(ordersId);
    }
}
