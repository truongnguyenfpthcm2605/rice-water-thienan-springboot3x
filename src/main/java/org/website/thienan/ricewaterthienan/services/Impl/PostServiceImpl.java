package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.PostRequest;
import org.website.thienan.ricewaterthienan.dto.response.PostResponse;
import org.website.thienan.ricewaterthienan.entities.Post;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.PostMapper;
import org.website.thienan.ricewaterthienan.repositories.PostRepository;
import org.website.thienan.ricewaterthienan.services.PostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class PostServiceImpl implements PostService<PostResponse, PostRequest> {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "postActive", allEntries = true),
                    @CacheEvict(cacheNames = "postTitle", allEntries = true)
            }
    )
    public PostResponse save(PostRequest postRequest) {
        Post post = postMapper.post(postRequest);
        return postMapper.postResponse(postRepository.save(post));
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "post", key = "#PostRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "postActive", allEntries = true),
                    @CacheEvict(cacheNames = "postTitle", allEntries = true)
            }
    )
    public PostResponse update(PostRequest postRequest) {
        Post post = postMapper.post(postRequest);
        return postMapper.postResponse(postRepository.save(post));
    }

    @Override
    @Cacheable(cacheNames = "post", key = "#id", unless = "#result==null")
    public Optional<PostResponse> findById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Post Id : " + id));
        return Optional.of(postMapper.postResponse(post));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "post", key = "#id"),
                    @CacheEvict(cacheNames = "postActive", allEntries = true),
                    @CacheEvict(cacheNames = "postTitle", allEntries = true)
            }
    )
    public void deleteById(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "postActive")
    public Page<PostResponse> findByActive(Pageable pageable, Boolean active) {
        Page<Post> postPage = postRepository.findByActive(pageable, active);

        List<PostResponse> postResponses = postPage.getContent().stream()
                .map(post -> {
                    PostResponse postResponse = postMapper.postResponse(post);
                    return postResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(postResponses, pageable, postPage.getTotalElements());
    }

    @Override
    @Cacheable(cacheNames = "postTitle")
    public Page<PostResponse> findByTitle(Pageable pageable, String title, boolean Active) {
        Page<Post> postPage = postRepository.findByTitle(pageable, "%" + title + "%", Active);

        List<PostResponse> postResponses = postPage.getContent().stream()
                .map(post -> {
                    PostResponse postResponse = postMapper.postResponse(post);
                    return postResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(postResponses, pageable, postPage.getTotalElements());
    }
}
