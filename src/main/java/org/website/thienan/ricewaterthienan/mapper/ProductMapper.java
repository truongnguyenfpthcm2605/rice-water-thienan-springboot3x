package org.website.thienan.ricewaterthienan.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.ProductRequest;
import org.website.thienan.ricewaterthienan.dto.response.ProductResponse;
import org.website.thienan.ricewaterthienan.entities.*;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;
import org.website.thienan.ricewaterthienan.repositories.BranchRepository;
import org.website.thienan.ricewaterthienan.repositories.BrandRepository;
import org.website.thienan.ricewaterthienan.repositories.CategoriesRepository;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductMapper {
    private  final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final BrandRepository brandRepository;
    private final BranchRepository branchRepository;
    private final CategoriesRepository categoriesRepository;
    private final BrandMapper brandMapper;
    private final BranchMapper branchMapper;
    private final CategoriesMapper categoriesMapper;

    public Product product(ProductRequest productRequest){
        Product product = new Product();
        if(productRequest.getId()!=null){
            product.setId(productRequest.getId());
        }
        product.setName(productRequest.getName());
        product.setLink(productRequest.getLink());
        product.setViews(productRequest.getViews());
        product.setAvatar(productRequest.getAvatar());
        product.setPrice(productRequest.getPrice());
        product.setCost(productRequest.getCost());
        product.setContent(productRequest.getContent());
        product.setDescription(productRequest.getDescription());
        product.setActive(productRequest.getActive());
        log.info(this.getClass().getName() + ": "+ "Add account for product");
        Account account = accountRepository.findById(productRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not Found Account Id: " + productRequest.getAccountId()));
        product.setAccount(account);

        log.info(this.getClass().getName() + ": "+ "Add brand for product");
        Brand brand = brandRepository.findById(productRequest.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Not Found Brand Product Id: " + productRequest.getBrandId()));
        product.setBrand(brand);

        log.info(this.getClass().getName() + ": "+ "Add branch for product");
        Branch branch = branchRepository.findById(productRequest.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Not Found Branch Product Id: " + productRequest.getBranchId()));
        product.setBranch(branch);

        log.info(this.getClass().getName() + ": "+ "Add categories for product");
        product.setCategories(categories(productRequest.getCategories()));

        return  product;

    }


    public ProductResponse productResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .cost(product.getCost())
                .price(product.getPrice())
                .content(product.getContent())
                .description(product.getDescription())
                .views(product.getViews())
                .link(product.getLink())
                .active(product.getActive())
                .createAt(product.getCreateAt())
                .updateAt(product.getUpdateAt())
                .accountResponse(accountMapper.accountResponse(product.getAccount()))
                .branchResponse(branchMapper.branchResponse(product.getBranch()))
                .brandResponse(brandMapper.brandResponse(product.getBrand()))
                .categories(product.getCategories())
                .build();

    }
    private Set<Categories> categories(Set<String> categoryName){
        Set<Categories> categories = new HashSet<>();
        categoryName.stream().forEach(e -> {
            Categories categories1 = categoriesRepository.findByName(e).orElseThrow(()-> new ResourceNotFoundException("Not Found Category Name - Product Mapper" + e));
            categories.add(categories1);
        });
        return categories;
    }
}
