package org.website.thienan.ricewaterthienan.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.website.thienan.ricewaterthienan.entities.Orders;

public interface OrdersService {
    Orders save(Orders orders);

    Orders update(Orders orders);

    Optional<Orders> findById(String id);

    Optional<Orders> findByName(String name);

    Optional<Orders> findByPhone(String phone);

    void deleteById(String id);

    Page<Orders> findByKeyword(Pageable pageable, String keyword);

    Page<Orders> findByActive(Pageable pageable, Boolean active);

    Page<Orders> findByStatus(Pageable pageable, String status);
}
