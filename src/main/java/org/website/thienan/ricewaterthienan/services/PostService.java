package org.website.thienan.ricewaterthienan.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.website.thienan.ricewaterthienan.entities.Post;

public interface PostService {
    Post save(Post postRequest);

    Post update(Post postRequest);

    Optional<Post> findById(Integer id);

    void deleteById(Integer id);

    Page<Post> findByActive(Pageable pageable, Boolean active);

    Page<Post> findByTitle(Pageable pageable, String title, boolean Active);
}
