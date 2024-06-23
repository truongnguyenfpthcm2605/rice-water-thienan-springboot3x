package org.website.thienan.ricewaterthienan.controller.apiv1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@Tag(name = "Product Controller API")
@Slf4j
public class ProductController {
    private final AccountServices accountServices;
    private final ProductService productService;
    private final BranchService branchService;
    private final BrandService brandService;
    private final CategoriesService categoriesService;

    @Operation(summary = "find All page product", description = "Find Page Product")
    @GetMapping("/product/findAll/{page}")
    public ResponseEntity<MessageResponse> findAll(@Valid @NotNull @PathVariable Optional<Integer> page,
                                                   @NotNull @RequestParam(defaultValue = "true", required = false) Boolean active) {
        log.info("findAll page {}", page);
        Page<Product> products = productService.findByActive(
                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE, SortAndPage.getSortUp("createAt")), active);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get All Product")
                .data(products).build(),
                HttpStatus.OK);
    }


    @Operation(summary = "find All page Filter product", description = "Find Page Filter Product")
    @GetMapping("/product/findAllFilter/{page}")
    public ResponseEntity<MessageResponse> findFilter(@Valid @PathVariable Optional<Integer> page,
                                                      @NotNull @RequestParam String name,
                                                      @RequestParam Optional<Double> price,
                                                      @RequestParam Optional<Long> views,
                                                      @RequestParam Optional<LocalDateTime> create,
                                                      @RequestParam(required = false, defaultValue = "true") Boolean active) {
        log.info("findFilter page {}", page);
        Page<Product> products = productService.findAllFilter(
                SortAndPage.getPage(page.orElse(0),
                        SortAndPage.MAX_PAGE, SortAndPage.getSortUp("createAt")),
                name,
                price.orElse(10000.0),
                views.orElse(1L), active,
                create.orElse(LocalDateTime.now())
        );
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get All Product Filter")
                .data(products).build(),
                HttpStatus.OK);
    }

    @Operation(summary = "find By Id Product", description = "Find By Id Product (String)")
    @GetMapping("/product/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable String id) {
        log.info("findById {}", id);
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Product"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get Product ")
                .data(product).build(), HttpStatus.OK);
    }

    @Operation(summary = "Save product", description = "Save new product")
    @PostMapping("/product/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<MessageResponse> save(@Valid @RequestBody ProductRequest productRequest) {
        log.info("save product {}", productRequest);
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setLink(productRequest.getLink());
        product.setPrice(productRequest.getPrice());
        product.setCost(productRequest.getCost());
        product.setAvatar(productRequest.getAvatar());
        product.setDescription(productRequest.getDescription());
        product.setViews(productRequest.getViews());
        product.setContent(productRequest.getContent());
        product.setActive(true);
        product.setAccount(accountServices.findById(productRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")));
        product.setBranch(branchService.findById(productRequest.getBranchId()).orElseThrow(() -> new ResourceNotFoundException("Not found Branch")));
        product.setBrand(brandService.findById(productRequest.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Not found Brand")));
        product.setCategories(productRequest.getCategories().stream()
                .map(e -> categoriesService.findByName(e).orElseThrow(() -> new ResourceNotFoundException("Not found Category"))).collect(Collectors.toSet()));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Save product successfully!")
                .data(productService.save(product)).build(), HttpStatus.OK);
    }

    @Operation(summary = "Update product", description = "Update product")
    @PutMapping("/product/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<MessageResponse> update(@Valid @NotNull @PathVariable String id, @RequestBody ProductRequest productRequest) {
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
        productService.update(product);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Update product successfully! ")
                .data(id).build(), HttpStatus.OK);
    }


    @Operation(summary = "Delete product", description = "Delete product")
    @DeleteMapping("/product/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> delete(@Valid @NotNull @PathVariable String id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Product"));
        product.setActive(false);
        product.setUpdateAt(LocalDateTime.now());
        productService.update(product);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Delete product successfully! ")
                .data("success").build(), HttpStatus.OK);
    }
}
