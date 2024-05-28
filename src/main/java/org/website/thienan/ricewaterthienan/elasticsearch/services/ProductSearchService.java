package org.website.thienan.ricewaterthienan.elasticsearch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.website.thienan.ricewaterthienan.elasticsearch.documments.ProductDocument;
import org.website.thienan.ricewaterthienan.elasticsearch.repositories.ProductSearchRepository;
import org.website.thienan.ricewaterthienan.entities.Categories;
import org.website.thienan.ricewaterthienan.entities.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductSearchRepository productSearchRepository;

    public void saveProductSearch(Product product) {
        ProductDocument productDocument = ProductDocument.builder()
                .id(product.getId())
                .name(product.getName())
                .link(product.getLink())
                .price(product.getPrice())
                .cost(product.getCost())
                .avatar(product.getAvatar())
                .description(product.getDescription())
                .views(product.getViews())
                .content(product.getContent())
                .accountId(product.getAccount().getId())
                .branchId(product.getBranch().getId())
                .brandId(product.getBrand().getId())
                .categories(product.getCategories().stream().map(Categories::getName).collect(Collectors.toSet()))
                .active(product.getActive())
                .createAt(product.getCreateAt())
                .updateAt(product.getUpdateAt())
                .build();
        productSearchRepository.save(productDocument);
    }

    public void updateProductSearch(Product product) {
        Optional<ProductDocument> productDocument = productSearchRepository.findById(product.getId());
        if (productDocument.isPresent()) {
            ProductDocument productDoc = productDocument.get();
            productDoc.setName(product.getName());
            productDoc.setLink(product.getLink());
            productDoc.setPrice(product.getPrice());
            productDoc.setCost(product.getCost());
            productDoc.setAvatar(product.getAvatar());
            productDoc.setDescription(product.getDescription());
            productDoc.setViews(product.getViews());
            productDoc.setContent(product.getContent());
            productDoc.setAccountId(product.getAccount().getId());
            productDoc.setBranchId(product.getBranch().getId());
            productDoc.setBrandId(product.getBrand().getId());
            productDoc.setCreateAt(product.getCreateAt());
            productDoc.setUpdateAt(product.getUpdateAt());
            productDoc.setActive(product.getActive());
            productDoc.setCategories(product.getCategories().stream().map(Categories::getName).collect(Collectors.toSet()));
            productSearchRepository.save(productDoc);
        }

    }

    public void deleteByIdProductSearch(String id) {
        productSearchRepository.deleteById(id);
    }

    public Page<ProductDocument> search(Pageable pageable,
                                 String name,
                                 Double minPrice,
                                 Double maxPrice,
                                 LocalDateTime create,
                                 Long minViews,
                                 Boolean active) {
        return productSearchRepository.search(pageable,
                name,
                minPrice,
                maxPrice,
                create,
                minViews,
                active);
    }


    public void saveAll(List<Product> products) {
        for (Product product : products) {
            saveProductSearch(product);
        }
    }
}
