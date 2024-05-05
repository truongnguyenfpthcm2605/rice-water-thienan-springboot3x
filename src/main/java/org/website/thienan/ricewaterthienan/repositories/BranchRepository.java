package org.website.thienan.ricewaterthienan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.Branch;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository  extends JpaRepository<Branch, Integer> {

     Optional<Branch> findByName(String name);
     List<Branch> findByActive(Boolean active);
}
