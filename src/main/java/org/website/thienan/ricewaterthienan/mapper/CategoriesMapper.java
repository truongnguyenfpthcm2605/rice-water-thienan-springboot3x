package org.website.thienan.ricewaterthienan.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.CategoriesRequest;
import org.website.thienan.ricewaterthienan.dto.response.CategoriesResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.entities.Categories;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;

@Component
@RequiredArgsConstructor
public class CategoriesMapper {
    private  final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public Categories toCategories(CategoriesRequest categoriesRequest) {
        Categories categories = new Categories();
        if(categoriesRequest.getId()!=null){
            categories.setId(categoriesRequest.getId());
        }
        categories.setName(categoriesRequest.getName());
        categories.setLink(categoriesRequest.getLink());
        categories.setIntroduction(categoriesRequest.getIntroduction());
        categories.setContent(categoriesRequest.getContent());
        categories.setAvatar(categoriesRequest.getAvatar());
        categories.setImageHeader(categoriesRequest.getImageHeader());
        categories.setViews(categoriesRequest.getViews());
        categories.setActive(categoriesRequest.getActive());
        Account account = accountRepository.findById(categoriesRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not Found Categories Id: " + categoriesRequest.getAccountId()));
        categories.setAccount(account);
        return categories;
    }

    public CategoriesResponse categoriesResponse(Categories categories){
        return CategoriesResponse.builder()
                .id(categories.getId())
                .name(categories.getName())
                .link(categories.getLink())
                .introduction(categories.getIntroduction())
                .content(categories.getContent())
                .avatar(categories.getAvatar())
                .imageHeader(categories.getImageHeader())
                .views(categories.getViews())
                .active(categories.getActive())
                .createAt(categories.getCreateAt())
                .updateAt(categories.getUpdateAt())
                .accountResponse(accountMapper.accountResponse(categories.getAccount()))
                .build();
    }
}
