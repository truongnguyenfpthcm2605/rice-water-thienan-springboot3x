package org.website.thienan.ricewaterthienan.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.Product;

import java.time.LocalDateTime;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(
            "select o from tbl_product o " +
                    "where o.name like %:name% " +
                    "or (o.price between :price - 100000 and :price + 100000) " +
                    "or (o.createAt >= :create ) " +
                    "or (o.views >= :views ) " +
                    "and (o.active = :active )"
    )
    Page<Product> findAllFilter(Pageable pageable,
                                @Param("name") String name,
                                @Param("price") Double price,
                                @Param("views") Long views,
                                @Param("active") Boolean active,
                                @Param("create") LocalDateTime create);

    Page<Product> findByActive(Pageable pageable, Boolean active);

}
