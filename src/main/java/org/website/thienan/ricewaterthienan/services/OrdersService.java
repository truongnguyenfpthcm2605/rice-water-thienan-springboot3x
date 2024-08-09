package org.website.thienan.ricewaterthienan.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.website.thienan.ricewaterthienan.dto.request.OrdersRequest;
import org.website.thienan.ricewaterthienan.entities.Orders;

import java.util.Optional;

public interface OrdersService{
    Orders save(OrdersRequest orders);
    Orders update(OrdersRequest orders);
    Optional<Orders> findById(String id);
    Optional<Orders> findByName(String name);
    Optional<Orders> findByPhone(String phone);
    void deleteById(String id);
    Page<Orders> findByKeyword(Pageable pageable, String keyword);
    Page<Orders> findByActive(Pageable pageable,Boolean active);

    Page<Orders> findByStatus(Pageable pageable, String status);


}
