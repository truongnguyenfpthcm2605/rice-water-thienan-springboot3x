package org.website.thienan.ricewaterthienan.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.OrderDetail;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {
}
