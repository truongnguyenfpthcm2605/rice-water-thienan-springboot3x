package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
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
import org.website.thienan.ricewaterthienan.dto.request.PostRequest;
import org.website.thienan.ricewaterthienan.entities.Post;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.repositories.CategoriesPostRepository;
import org.website.thienan.ricewaterthienan.repositories.CategoriesRepository;
import org.website.thienan.ricewaterthienan.repositories.PostRepository;
import org.website.thienan.ricewaterthienan.services.PostService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoriesRepository categoriesRepository;
    private final CategoriesPostRepository categoriesPostRepository;
    private final AccountRepository accountRepository;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "postActive", allEntries = true),
                    @CacheEvict(cacheNames = "postTitle", allEntries = true)
            }
    )
    public Post save(PostRequest postRequest) {

        return postRepository.save(Post.builder()
                .title(postRequest.getTitle())
                .link(postRequest.getLink())
                .introduction(postRequest.getIntroduction())
                .content(postRequest.getContent())
                .avatar(postRequest.getAvatar())
                .imageHeader(postRequest.getImageHeader())
                .views(1L)
                .active(Boolean.TRUE)
                .categories(postRequest.getCategories().stream().map(e -> categoriesRepository.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not found Category"))).collect(Collectors.toSet()))
                .account(accountRepository.findById(postRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")))
                .categoryPost(categoriesPostRepository.findById(postRequest.getCategoriesPostId()).orElseThrow(() -> new ResourceNotFoundException("Not found Category Post")))
                .build());
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
    public Post update(PostRequest postRequest) {
        return postRepository.save(Post.builder()
                .title(postRequest.getTitle())
                .link(postRequest.getLink())
                .introduction(postRequest.getIntroduction())
                .content(postRequest.getContent())
                .avatar(postRequest.getAvatar())
                .imageHeader(postRequest.getImageHeader())
                .views(postRequest.getViews())
                .active(postRequest.getActive())
                .categories(postRequest.getCategories().stream().map(e -> categoriesRepository.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not found Category"))).collect(Collectors.toSet()))
                .account(accountRepository.findById(postRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")))
                .categoryPost(categoriesPostRepository.findById(postRequest.getCategoriesPostId()).orElseThrow(() -> new ResourceNotFoundException("Not found Category Post")))
                .build());
    }

    @Override
    @Cacheable(cacheNames = "post", key = "#id", unless = "#result==null")
    public Optional<Post> findById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Post Id : " + id));
        return Optional.of(post);
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
    public Page<Post> findByActive(Pageable pageable, Boolean active) {
        return postRepository.findByActive(pageable, active);

    }

    @Override
    @Cacheable(cacheNames = "postTitle")
    public Page<Post> findByTitle(Pageable pageable, String title, boolean Active) {
        return postRepository.findByTitle(pageable, "%" + title + "%", Active);

    }
}
