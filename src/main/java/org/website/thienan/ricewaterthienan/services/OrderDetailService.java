package org.website.thienan.ricewaterthienan.services;

import java.util.List;
import java.util.Optional;

import org.website.thienan.ricewaterthienan.entities.OrderDetail;

public interface OrderDetailService {
    OrderDetail save(OrderDetail orderDetail);

    OrderDetail update(OrderDetail orderDetail);

    List<OrderDetail> saveAll(List<OrderDetail> list);

    Optional<OrderDetail> findById(Integer id);

    void deleteById(Integer id);

    List<OrderDetail> findAll();

    List<OrderDetail> findByProductId(String productId);

    List<OrderDetail> findByOrdersId(String ordersId);
}
