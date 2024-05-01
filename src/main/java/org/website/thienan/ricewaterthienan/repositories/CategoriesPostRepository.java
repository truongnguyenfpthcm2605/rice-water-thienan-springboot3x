package org.website.thienan.ricewaterthienan.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.CategoriesPost;

@Repository
public interface CategoriesPostRepository extends CrudRepository<CategoriesPost, Integer> {
}
