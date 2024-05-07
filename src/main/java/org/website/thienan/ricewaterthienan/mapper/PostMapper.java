package org.website.thienan.ricewaterthienan.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.PostRequest;
import org.website.thienan.ricewaterthienan.dto.response.CategoriesResponse;
import org.website.thienan.ricewaterthienan.dto.response.PostResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.entities.Categories;
import org.website.thienan.ricewaterthienan.entities.CategoriesPost;
import org.website.thienan.ricewaterthienan.entities.Post;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.repositories.CategoriesPostRepository;
import org.website.thienan.ricewaterthienan.repositories.CategoriesRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostMapper {

    private final CategoriesPostMapper categoriesPostMapper;
    private final CategoriesMapper categoriesMapper;
    private final AccountMapper accountMapper;
    private final CategoriesRepository categoriesRepository;
    private final AccountRepository accountRepository;
    private final CategoriesPostRepository categoriesPostRepository;

    public Post post(PostRequest postRequest){
        Post post = new Post();
        if(postRequest.getId() != null){
            post.setId(postRequest.getId());
        }
        post.setTitle(postRequest.getTitle());
        post.setLink(postRequest.getLink());
        post.setContent(postRequest.getContent());
        post.setIntroduction(postRequest.getIntroduction());
        post.setViews(postRequest.getViews());
        post.setAvatar(postRequest.getAvatar());
        post.setImageHeader(postRequest.getImageHeader());
        post.setActive(postRequest.getActive());
        log.info("Get Categories Post in Post Mapper");
        CategoriesPost categoriesPost = categoriesPostRepository.findById(postRequest.getCategoriesPostId()).orElseThrow(() -> new ResourceNotFoundException("Not found Category Post in Post Mapper where ID : "+ postRequest.getCategoriesPostId()));
        post.setCategoryPost(categoriesPost);

        log.info("Get Account Post in Post Mapper");
        Account account = accountRepository.findById(postRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account in Post Mapper where ID : "+ postRequest.getAccountId()));
        post.setAccount(account);

        log.info("Get Categories  in Post Mapper");
        post.setCategories(categories(postRequest.getCategories()));

        return post;

    }

    public PostResponse postResponse(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .link(post.getLink())
                .content(post.getContent())
                .introduction(post.getIntroduction())
                .avatar(post.getAvatar())
                .imageHeader(post.getImageHeader())
                .active(post.getActive())
                .views(post.getViews())
                .createAt(post.getCreateAt())
                .updateAt(post.getUpdateAt())
                .categoriesResponses(getCategoriesResponses(post.getCategories()))
                .categoriesPostResponse(categoriesPostMapper.categoriesPostResponse(post.getCategoryPost()))
                .accountResponse(accountMapper.accountResponse(post.getAccount()))
                .build();

    }

    private Set<Categories> categories(Set<Integer> categoryName){
        Set<Categories> categories = new HashSet<>();
        categoryName.stream().forEach(e -> {
            Categories categories1 = categoriesRepository.findById(e).orElseThrow(()-> new ResourceNotFoundException("Not Found Category Id - Post Mapper" + e));
            categories.add(categories1);
        });
        return categories;
    }

    private List<CategoriesResponse> getCategoriesResponses(Set<Categories> categories){

        return  categories.stream().map(e -> {
            CategoriesResponse categoriesResponse = categoriesMapper.categoriesResponse(e);
            return categoriesResponse;
        }).collect(Collectors.toList());


    }
}
