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
import org.website.thienan.ricewaterthienan.dto.request.OrdersRequest;
import org.website.thienan.ricewaterthienan.dto.response.OrdersResponse;
import org.website.thienan.ricewaterthienan.entities.Orders;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.OrdersMapper;
import org.website.thienan.ricewaterthienan.repositories.OrdersRepository;
import org.website.thienan.ricewaterthienan.services.OrdersService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class OrdersServiceImpl implements OrdersService<OrdersResponse, OrdersRequest> {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "ordersKeyWord", allEntries = true),
                    @CacheEvict(cacheNames = "ordersActive", allEntries = true),
                    @CacheEvict(cacheNames = "ordersStatus", allEntries = true)
            }
    )
    public OrdersResponse save(OrdersRequest orders) {
        Orders orders1 = ordersMapper.orders(orders);
        return ordersMapper.ordersResponse(ordersRepository.save(orders1));
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "orders", key="#OrdersRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "ordersKeyWord", allEntries = true),
                    @CacheEvict(cacheNames = "ordersActive", allEntries = true),
                    @CacheEvict(cacheNames = "ordersStatus", allEntries = true)
            }
    )
    public OrdersResponse update(OrdersRequest orders) {
        Orders orders1 = ordersMapper.orders(orders);
        return ordersMapper.ordersResponse(ordersRepository.saveAndFlush(orders1));
    }

    @Override
    @Cacheable(cacheNames = "orders",key = "#id", unless = "#result==null")
    public Optional<OrdersResponse> findById(String id) {
        Orders orders = ordersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Orders ID : "+ id));
        return Optional.of(ordersMapper.ordersResponse(orders));
    }

    @Override
    @Cacheable(cacheNames = "orders",key = "#name", unless = "#result==null")
    public Optional<OrdersResponse> findByName(String name) {
        Orders orders = ordersRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found Orders NAME : "+ name));
        return Optional.of(ordersMapper.ordersResponse(orders));
    }

    @Override
    @Cacheable(cacheNames = "orders",key = "#phone", unless = "#result==null")
    public Optional<OrdersResponse> findByPhone(String phone) {
        Orders orders = ordersRepository.findByPhone(phone).orElseThrow(() -> new ResourceNotFoundException("Not found Orders PHONE : "+ phone));
        return Optional.of(ordersMapper.ordersResponse(orders));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "orders", key = "#id"),
                    @CacheEvict(cacheNames = "ordersKeyWord", allEntries = true),
                    @CacheEvict(cacheNames = "ordersActive", allEntries = true),
                    @CacheEvict(cacheNames = "ordersStatus", allEntries = true)
            }
    )
    public void deleteById(String id) {
        ordersRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "ordersKeyWord")
    public Page<OrdersResponse> findByKeyword(Pageable pageable, String keyword) {
        Page<Orders> ordersPage = ordersRepository.findByKeyword(pageable, "%"+keyword+"%");

        List<OrdersResponse> ordersResponses = ordersPage.getContent().stream()
                .map(orders -> {
                    OrdersResponse ordersResponse = ordersMapper.ordersResponse(orders);
                    return ordersResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(ordersResponses, pageable, ordersPage.getTotalElements());
    }

    @Override
    @Cacheable(cacheNames = "ordersActive")
    public Page<OrdersResponse> findByActive(Pageable pageable, Boolean active) {
        Page<Orders> ordersPage = ordersRepository.findByActive(pageable, active);

        List<OrdersResponse> ordersResponses = ordersPage.getContent().stream()
                .map(orders -> {
                    OrdersResponse ordersResponse = ordersMapper.ordersResponse(orders);
                    return ordersResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(ordersResponses, pageable, ordersPage.getTotalElements());
    }

    @Override
    @Cacheable(cacheNames = "ordersStatus")
    public Page<OrdersResponse> findByStatus(Pageable pageable, String status) {
        Page<Orders> ordersPage = ordersRepository.findByStatus(pageable, status);

        List<OrdersResponse> ordersResponses = ordersPage.getContent().stream()
                .map(orders -> {
                    OrdersResponse ordersResponse = ordersMapper.ordersResponse(orders);
                    return ordersResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(ordersResponses, pageable, ordersPage.getTotalElements());
    }
}
