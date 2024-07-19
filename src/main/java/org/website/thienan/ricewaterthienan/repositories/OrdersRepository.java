package org.website.thienan.ricewaterthienan.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.Orders;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {
    Optional<Orders> findByName(String name);

    Optional<Orders> findByPhone(String phone);

    Page<Orders> findByActive(Pageable pageable, Boolean active);

    @Query("select o from Orders  o where  o.status =:status and o.active =true")
    Page<Orders> findByStatus(Pageable pageable, @Param("status") String status);

    @Query(
            "select  o from Orders  o where o.name like :keyword or o.address like :keyword or o.phone like :keyword and o.active = true")
    Page<Orders> findByKeyword(Pageable pageable, @Param("keyword") String keyword);
}
