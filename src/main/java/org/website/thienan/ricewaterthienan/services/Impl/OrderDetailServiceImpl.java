package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.OrdersDetailRequest;
import org.website.thienan.ricewaterthienan.entities.OrderDetail;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.OrderDetailRepository;
import org.website.thienan.ricewaterthienan.repositories.OrdersRepository;
import org.website.thienan.ricewaterthienan.repositories.ProductRepository;
import org.website.thienan.ricewaterthienan.services.OrderDetailService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "orderDetails", allEntries = true),
                    @CacheEvict(cacheNames = "ordersDetailProduct", allEntries = true),
                    @CacheEvict(cacheNames = "ordersDetailOrders", allEntries = true)
            }
    )
    public List<OrderDetail> saveAll(List<OrdersDetailRequest> list) {
        List<OrderDetail> orderDetails = list.stream().map(ordersDetailRequest ->
                OrderDetail.builder()
                        .quantity(ordersDetailRequest.getQuantity())
                        .price(ordersDetailRequest.getPrice())
                        .product(productRepository.findById(ordersDetailRequest.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")))
                        .order(ordersRepository.findById(ordersDetailRequest.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("Order not found")))
                        .build()
        ).collect(Collectors.toList());
        return orderDetailRepository.saveAllAndFlush(orderDetails);
    }


    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "orderDetails", allEntries = true),
                    @CacheEvict(cacheNames = "ordersDetailProduct", allEntries = true),
                    @CacheEvict(cacheNames = "ordersDetailOrders", allEntries = true)
            }
    )
    public OrderDetail save(OrdersDetailRequest ordersDetailRequest) {
        return orderDetailRepository.save(OrderDetail.builder()
                .quantity(ordersDetailRequest.getQuantity())
                .price(ordersDetailRequest.getPrice())
                .product(productRepository.findById(ordersDetailRequest.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")))
                .order(ordersRepository.findById(ordersDetailRequest.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("Order not found")))
                .build());
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
    public OrderDetail update(OrdersDetailRequest ordersDetailRequest) {
        return orderDetailRepository.save(OrderDetail.builder()
                .quantity(ordersDetailRequest.getQuantity())
                .price(ordersDetailRequest.getPrice())
                .product(productRepository.findById(ordersDetailRequest.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")))
                .order(ordersRepository.findById(ordersDetailRequest.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("Order not found")))
                .build());
    }

    @Override
    @Cacheable(cacheNames = "orderDetail", key = "#id", unless = "#result==null")
    public Optional<OrderDetail> findById(Integer id) {
       OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found OrderDetail Id : " + id));
       return Optional.of(orderDetail);
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
