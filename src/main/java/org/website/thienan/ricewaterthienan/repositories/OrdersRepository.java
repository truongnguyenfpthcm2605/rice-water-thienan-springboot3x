package org.website.thienan.ricewaterthienan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {
}
