package org.website.thienan.ricewaterthienan.services;

import org.website.thienan.ricewaterthienan.entities.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    OrderDetail save(OrderDetail orderDetail);
    OrderDetail update(OrderDetail orderDetail);

    Optional<OrderDetail> findById(Integer id);

    void deleteById(Integer id);

    List<OrderDetail> findAll();

    List<OrderDetail> findByProductId(String productId);
    List<OrderDetail> findByOrdersId(String ordersId);

}
