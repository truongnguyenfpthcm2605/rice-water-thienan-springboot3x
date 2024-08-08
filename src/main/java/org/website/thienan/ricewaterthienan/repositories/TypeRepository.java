package org.website.thienan.ricewaterthienan.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

    Page<Type> findByActive(Pageable pageable, Boolean active);

    @Query("select o from tbl_type  o where o.title like :title and o.active = :Active")
    Page<Type> findByTitle(Pageable pageable, @Param("title") String title, @Param("Active") boolean Active);
}
