package org.website.thienan.ricewaterthienan.services;


import org.website.thienan.ricewaterthienan.entities.RoleDetail;

import java.util.List;
import java.util.Optional;

public interface RoleDetailService {
    RoleDetail save(RoleDetail roleDetail);
    RoleDetail update(RoleDetail roleDetail);
    Optional<RoleDetail> findById(Integer id);
    Optional<RoleDetail> findByName(String name);
    void deleteById(Integer id);
    List<RoleDetail> findAll();
    List<RoleDetail> findByActive(Boolean active);

}
