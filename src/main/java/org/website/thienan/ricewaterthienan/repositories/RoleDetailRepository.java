package org.website.thienan.ricewaterthienan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.RoleDetail;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDetailRepository extends JpaRepository<RoleDetail, Integer> {
    Optional<RoleDetail> findByName(String name);
    List<RoleDetail> findByActive(Boolean active);
}
