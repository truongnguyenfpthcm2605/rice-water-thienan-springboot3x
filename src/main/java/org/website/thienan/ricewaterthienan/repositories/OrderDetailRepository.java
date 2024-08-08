package org.website.thienan.ricewaterthienan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    @Query("select o from tbl_order_detail  o where o.product.id = :productId")
    List<OrderDetail> findByProductId(@Param("productId") String productId);

    @Query("select o from tbl_order_detail o where o.order.id = :ordersId")
    List<OrderDetail> findByOrdersId(@Param("ordersId") String ordersId);
}
