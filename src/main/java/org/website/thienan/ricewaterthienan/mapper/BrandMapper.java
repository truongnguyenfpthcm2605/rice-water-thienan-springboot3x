package org.website.thienan.ricewaterthienan.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.BrandRequest;
import org.website.thienan.ricewaterthienan.dto.response.BrandResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.entities.Brand;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BrandMapper {

    private  final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ProductMapper productMapper;
    public Brand brand(BrandRequest brandRequest) {
        Brand brand = new Brand();
        if(brandRequest.getId() != null){
            brand.setId(brandRequest.getId());
        }
        brand.setName(brandRequest.getName());
        brand.setAvatar(brandRequest.getAvatar());
        brand.setViews(brandRequest.getViews());
        brand.setActive(brandRequest.getActive());
        Account account = accountRepository.findById(brandRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not Found Account - Branch Id: " + brandRequest.getAccountId()));
        brand.setAccount(account);
        return brand;
    }
    public BrandResponse brandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .views(brand.getViews())
                .active(brand.getActive())
                .createAt(brand.getCreateAt())
                .updateAt(brand.getUpdateAt())
                .accountResponse(accountMapper.accountResponse(brand.getAccount()))
                .productResponses(brand.getProducts().stream().map(e -> productMapper.productResponse(e)).collect(Collectors.toList()))
                .build();
    }
}
