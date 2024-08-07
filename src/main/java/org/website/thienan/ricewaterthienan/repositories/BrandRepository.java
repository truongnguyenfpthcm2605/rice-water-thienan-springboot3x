package org.website.thienan.ricewaterthienan.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Optional<Brand> findByName(String name);

    List<Brand> findByActive(Boolean active);
}
