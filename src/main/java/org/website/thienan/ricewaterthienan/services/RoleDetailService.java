package org.website.thienan.ricewaterthienan.services;

import java.util.List;
import java.util.Optional;

import org.website.thienan.ricewaterthienan.entities.RoleDetail;

public interface RoleDetailService {
    RoleDetail save(RoleDetail roleDetail);

    RoleDetail update(RoleDetail roleDetail);

    Optional<RoleDetail> findById(Integer id);

    Optional<RoleDetail> findByName(String name);

    void deleteById(Integer id);

    List<RoleDetail> findAll();

    List<RoleDetail> findByActive(Boolean active);
}
