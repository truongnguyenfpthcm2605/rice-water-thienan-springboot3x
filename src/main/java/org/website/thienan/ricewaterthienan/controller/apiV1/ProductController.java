package org.website.thienan.ricewaterthienan.controller.apiV1;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.request.ProductRequest;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.Product;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.*;
import org.website.thienan.ricewaterthienan.utils.SortAndPage;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
public class ProductController {
    private final AccountServices accountServices;
    private final ProductService productService;
    private final BranchService branchService;
    private final BrandService brandService;
    private final CategoriesService categoriesService;

    @GetMapping("/product/findAll/{page}")
    public ResponseEntity<MessageResponse> findAll(@PathVariable("page") Optional<Integer> page, @RequestParam("active") Boolean active) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .message("Get All Product")
                .data(productService.findByActive(
                        SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE, SortAndPage.getSortUp("createAt")), active
                )).build(),
                HttpStatus.OK);
    }

    @GetMapping("/product/findAllFilter/{page}")
    public ResponseEntity<MessageResponse> findFilter(@PathVariable("page") Optional<Integer> page,
                                                      @RequestParam("name") String name,
                                                      @RequestParam("price") Optional<Double> price,
                                                      @RequestParam("views") Optional<Long> views,
                                                      @RequestParam("create") Optional<LocalDateTime> create,
                                                      @RequestParam("active") Boolean active) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .message("Get All Product Filter")
                .data(productService.findAllFilter(
                        SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE, SortAndPage.getSortUp("createAt")),
                        name, price.orElse(10000.0), views.orElse(1L), active, create.orElse(LocalDateTime.now())
                )).build(),
                HttpStatus.OK);
    }

    @GetMapping("/product/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@PathVariable("id") String id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Product"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get Product ")
                .data(product).build(), HttpStatus.OK);
    }

    @PostMapping("/product/save")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> save(@RequestBody ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setLink(productRequest.getLink());
        product.setPrice(productRequest.getPrice());
        product.setCost(productRequest.getCost());
        product.setAvatar(productRequest.getAvatar());
        product.setDescription(productRequest.getDescription());
        product.setViews(1L);
        product.setContent(productRequest.getContent());
        product.setActive(true);
        product.setAccount(accountServices.findById(productRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")));
        product.setBranch(branchService.findById(productRequest.getBranchId()).orElseThrow(() -> new ResourceNotFoundException("Not found Branch")));
        product.setBrand(brandService.findById(productRequest.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Not found Brand")));
        product.setCategories(productRequest.getCategories().stream()
                .map(e -> categoriesService.findByName(e).orElseThrow(() -> new ResourceNotFoundException("Not found Category"))).collect(Collectors.toSet()));

        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Save product successfully! ")
                .data(productService.save(product)).build(), HttpStatus.OK);
    }

    @PutMapping("/product/update/{id}")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> update(@PathVariable("id") String id, @RequestBody ProductRequest productRequest) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Product"));
        product.setName(productRequest.getName());
        product.setLink(productRequest.getLink());
        product.setPrice(productRequest.getPrice());
        product.setCost(productRequest.getCost());
        product.setAvatar(productRequest.getAvatar());
        product.setDescription(productRequest.getDescription());
        product.setViews(productRequest.getViews());
        product.setContent(productRequest.getContent());
        product.setActive(productRequest.getActive());
        product.setAccount(accountServices.findById(productRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")));
        product.setBranch(branchService.findById(productRequest.getBranchId()).orElseThrow(() -> new ResourceNotFoundException("Not found Branch")));
        product.setBrand(brandService.findById(productRequest.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Not found Brand")));
        product.setCategories(productRequest.getCategories().stream()
                .map(e -> categoriesService.findByName(e).orElseThrow(() -> new ResourceNotFoundException("Not found Category"))).collect(Collectors.toSet()));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Update product successfully! ")
                .data(productService.update(product)).build(), HttpStatus.OK);
    }

    @PutMapping("/product/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") String id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Product"));
        product.setActive(false);
        product.setUpdateAt(LocalDateTime.now());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Update product successfully! ")
                .data(productService.update(product)).build(), HttpStatus.OK);
    }
}
