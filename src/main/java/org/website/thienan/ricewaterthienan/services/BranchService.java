package org.website.thienan.ricewaterthienan.services;

import org.website.thienan.ricewaterthienan.dto.request.BranchRequest;
import org.website.thienan.ricewaterthienan.entities.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchService {
    Branch save(BranchRequest branchRequest);
    Branch update(BranchRequest branchRequest);
    Optional<Branch> findById(Integer id);
    Optional<Branch> findByName(String name);
    void deleteById(Integer id);
    List<Branch> findAll();
    List<Branch> findByActive(Boolean active);

}
