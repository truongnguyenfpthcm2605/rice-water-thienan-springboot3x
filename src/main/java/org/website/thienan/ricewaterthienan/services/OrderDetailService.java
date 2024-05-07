package org.website.thienan.ricewaterthienan.services;

import org.website.thienan.ricewaterthienan.dto.response.OrderdetailResponse;
import org.website.thienan.ricewaterthienan.entities.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    OrderdetailResponse save(OrderDetail orderDetail);
    OrderdetailResponse update(OrderDetail orderDetail);

    Optional<OrderdetailResponse> findById(Integer id);

    void deleteById(Integer id);

    List<OrderdetailResponse> findAll();

    List<OrderdetailResponse> findByProductId(String productId);
    List<OrderdetailResponse> findByOrdersId(String ordersId);

}
