package org.website.thienan.ricewaterthienan.services.Impl;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.entities.Post;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.PostRepository;
import org.website.thienan.ricewaterthienan.services.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Caching(
            evict = {
                @CacheEvict(cacheNames = "postActive", allEntries = true),
                @CacheEvict(cacheNames = "postTitle", allEntries = true)
            })
    public Post save(Post postRequest) {
        return postRepository.save(postRequest);
    }

    @Override
    @Caching(
            put = {@CachePut(cacheNames = "post", key = "#PostRequest.id")},
            evict = {
                @CacheEvict(cacheNames = "postActive", allEntries = true),
                @CacheEvict(cacheNames = "postTitle", allEntries = true)
            })
    public Post update(Post postRequest) {
        return postRepository.save(postRequest);
    }

    @Override
    @Cacheable(cacheNames = "post", key = "#id", unless = "#result==null")
    public Optional<Post> findById(Integer id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Post Id : " + id));
        return Optional.of(post);
    }

    @Override
    @Caching(
            evict = {
                @CacheEvict(cacheNames = "post", key = "#id"),
                @CacheEvict(cacheNames = "postActive", allEntries = true),
                @CacheEvict(cacheNames = "postTitle", allEntries = true)
            })
    public void deleteById(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "postActive")
    public Page<Post> findByActive(Pageable pageable, Boolean active) {
        return postRepository.findByActive(pageable, active);
    }

    @Override
    @Cacheable(cacheNames = "postTitle")
    public Page<Post> findByTitle(Pageable pageable, String title, boolean Active) {
        return postRepository.findByTitle(pageable, "%" + title + "%", Active);
    }
}
