package org.website.thienan.ricewaterthienan.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.CategoriesPostRequest;
import org.website.thienan.ricewaterthienan.dto.response.CategoriesPostResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.entities.CategoriesPost;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoriesPostMapper {
    private  final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PostMapper postMapper;

    public CategoriesPost categoriesPost(CategoriesPostRequest categoriesPostRequest){
        CategoriesPost categoriesPost = new CategoriesPost();
        if(categoriesPost.getId()!=null){
            categoriesPost.setId(categoriesPostRequest.getId());
        }
        categoriesPost.setName(categoriesPostRequest.getName());
        categoriesPost.setLink(categoriesPostRequest.getLink());
        categoriesPost.setViews(categoriesPostRequest.getViews());
        categoriesPost.setActive(categoriesPostRequest.getActive());
        Account account = accountRepository.findById(categoriesPostRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not Found categoriesPost Id: " + categoriesPostRequest.getAccountId()));
        categoriesPost.setAccount(account);
        return categoriesPost;
    }

    public CategoriesPostResponse categoriesPostResponse(CategoriesPost categoriesPost){
        return  CategoriesPostResponse.builder()
                .id(categoriesPost.getId())
                .name(categoriesPost.getName())
                .link(categoriesPost.getLink())
                .views(categoriesPost.getViews())
                .active(categoriesPost.getActive())
                .createAt(categoriesPost.getCreateAt())
                .updateAt(categoriesPost.getUpdateAt())
                .accountResponse(accountMapper.accountResponse(categoriesPost.getAccount()))
                .posts(categoriesPost.getPosts().stream().map(e -> postMapper.postResponse(e)).collect(Collectors.toList()))
                .build();
    }
}
