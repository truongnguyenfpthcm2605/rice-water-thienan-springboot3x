package org.website.thienan.ricewaterthienan.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.Branch;

@Repository
public interface BranchRepository  extends CrudRepository<Branch, Integer> {
}
