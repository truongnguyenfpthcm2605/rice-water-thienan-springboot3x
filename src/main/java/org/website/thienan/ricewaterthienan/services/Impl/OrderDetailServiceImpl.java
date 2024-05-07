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
import org.website.thienan.ricewaterthienan.dto.response.OrderdetailResponse;
import org.website.thienan.ricewaterthienan.entities.OrderDetail;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.OrderDetailMapper;
import org.website.thienan.ricewaterthienan.repositories.OrderDetailRepository;
import org.website.thienan.ricewaterthienan.services.OrderDetailService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "orderDetails", allEntries = true),
                    @CacheEvict(cacheNames = "ordersDetailProduct", allEntries = true),
                    @CacheEvict(cacheNames = "ordersDetailOrders", allEntries = true)
            }
    )
    public OrderdetailResponse save(OrderDetail orderDetail) {
        return orderDetailMapper.orderdetailResponse(orderDetailRepository.save(orderDetail));
    }

    @Override
    @Caching(
            put = {
                @CachePut(cacheNames = "orderDetail" , key = "#OrderDetail.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "orderDetails", allEntries = true),
                    @CacheEvict(cacheNames = "ordersDetailProduct", allEntries = true),
                    @CacheEvict(cacheNames = "ordersDetailOrders", allEntries = true)
            }
    )
    public OrderdetailResponse update(OrderDetail orderDetail) {
        return orderDetailMapper.orderdetailResponse(orderDetailRepository.saveAndFlush(orderDetail));
    }

    @Override
    @Cacheable(cacheNames = "orderDetail", key = "#id", unless = "#result==null")
    public Optional<OrderdetailResponse> findById(Integer id) {
       OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found OrderDetail Id : " + id));
       return Optional.of(orderDetailMapper.orderdetailResponse(orderDetail));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "orderDetail", key="#id"),
                    @CacheEvict(cacheNames = "orderDetails", allEntries = true),
                    @CacheEvict(cacheNames = "ordersDetailProduct", allEntries = true),
                    @CacheEvict(cacheNames = "ordersDetailOrders", allEntries = true)
            }
    )
    public void deleteById(Integer id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "orderDetails")
    public List<OrderdetailResponse> findAll() {
        return orderDetailRepository.findAll().stream().map( orderDetail -> {
            OrderdetailResponse orderdetailResponse = orderDetailMapper.orderdetailResponse(orderDetail);
            return  orderdetailResponse;
        }).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "ordersDetailProduct")
    public List<OrderdetailResponse> findByProductId(String productId) {
        return orderDetailRepository.findByProductId(productId).stream().map(ords -> {
            OrderdetailResponse orderDetailResponse1 = orderDetailMapper.orderdetailResponse(ords);
            return  orderDetailResponse1;
        }).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "ordersDetailOrders")
    public List<OrderdetailResponse> findByOrdersId(String ordersId) {
        return orderDetailRepository.findByOrdersId(ordersId).stream().map(ords -> {
            OrderdetailResponse orderDetailResponse1 = orderDetailMapper.orderdetailResponse(ords);
            return  orderDetailResponse1;
        }).collect(Collectors.toList());
    }
}
