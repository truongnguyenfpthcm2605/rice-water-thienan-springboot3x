package org.website.thienan.ricewaterthienan.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.TypeRequest;
import org.website.thienan.ricewaterthienan.dto.response.PostResponse;
import org.website.thienan.ricewaterthienan.dto.response.TypeResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.entities.Post;
import org.website.thienan.ricewaterthienan.entities.Type;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.repositories.PostRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TypeMapper {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Type type(TypeRequest typeRequest) {
        Type type = new Type();
        if (typeRequest.getId() != null) type.setId(typeRequest.getId());
        type.setTitle(typeRequest.getTitle());
        type.setLink(typeRequest.getLink());
        type.setContent(typeRequest.getContent());
        type.setIntroduction(typeRequest.getIntroduction());
        type.setViews(typeRequest.getViews());
        type.setAvatar(typeRequest.getAvatar());
        type.setImageHeader(typeRequest.getImageHeader());
        type.setActive(typeRequest.getActive());
        log.info("Add account in type Mapper");
        Account account = accountRepository.findById(typeRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account in Type Mapper where ID : " + typeRequest.getAccountId()));
        type.setAccount(account);
        log.info("Add Post in type Mapper");
        type.setPosts(getPosts(typeRequest.getTypePost()));
        return type;
    }

    public TypeResponse typeResponse(Type type) {
        return TypeResponse.builder()
                .id(type.getId())
                .title(type.getTitle())
                .link(type.getLink())
                .content(type.getContent())
                .introduction(type.getIntroduction())
                .avatar(type.getAvatar())
                .imageHeader(type.getImageHeader())
                .views(type.getViews())
                .active(type.getActive())
                .createAt(type.getCreateAt())
                .updateAt(type.getUpdateAt())
                .postResponses(getPostResponses(type.getPosts()))
                .accountResponse(accountMapper.accountResponse(type.getAccount()))
                .build();
    }

    private Set<Post> getPosts(Set<Integer> postIds) {
        return postIds.stream().map(e -> {
            Post post = postRepository.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not found Post Id in Type Mapper : " + e));
            return post;
        }).collect(Collectors.toSet());
    }

    private Set<PostResponse> getPostResponses(Set<Post> posts) {
        return posts.stream().map(e -> {
            PostResponse postResponse = postMapper.postResponse(e);
            return postResponse;
        }).collect(Collectors.toSet());
    }
}
