package org.website.thienan.ricewaterthienan.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByActive(Pageable pageable, Boolean active);

    @Query("select o from Post  o where o.title like :title and o.active = :Active")
    Page<Post> findByTitle(Pageable pageable, @Param("title") String title,@Param("Active") boolean Active);
}
