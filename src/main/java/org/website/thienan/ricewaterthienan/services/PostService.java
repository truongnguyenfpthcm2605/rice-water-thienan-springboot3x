package org.website.thienan.ricewaterthienan.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.website.thienan.ricewaterthienan.dto.request.PostRequest;
import org.website.thienan.ricewaterthienan.entities.Post;

import java.util.Optional;

public interface PostService{
    Post save(PostRequest postRequest);
    Post update(PostRequest postRequest);
    Optional<Post> findById(Integer id);
    void deleteById(Integer id);
    Page<Post> findByActive(Pageable pageable, Boolean active);
    Page<Post> findByTitle( Pageable pageable, String title, boolean Active);
}
