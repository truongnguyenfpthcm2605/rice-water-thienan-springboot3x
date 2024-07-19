package org.website.thienan.ricewaterthienan.services;

import java.util.List;
import java.util.Optional;

import org.website.thienan.ricewaterthienan.entities.Branch;

public interface BranchService {
    Branch save(Branch branchRequest);

    Branch update(Branch branchRequest);

    Optional<Branch> findById(Integer id);

    Optional<Branch> findByName(String name);

    void deleteById(Integer id);

    List<Branch> findAll();

    List<Branch> findByActive(Boolean active);
}
