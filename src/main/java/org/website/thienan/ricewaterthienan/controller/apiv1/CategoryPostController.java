package org.website.thienan.ricewaterthienan.controller.apiv1;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.request.CategoriesPostRequest;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.CategoriesPost;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.CategoriesPostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
@Tag(name = "Category Post Controller API")
@Slf4j
public class CategoryPostController {
    private final CategoriesPostService categoriesPostService;
    private final AccountServices accountServices;

    @Operation(summary = "Find All Category Post", description = "Find All Category Post")
    @GetMapping("/category-post/findAll")
    public ResponseEntity<MessageResponse> findAll() {
        log.info("Find All Category Post");
        Iterable<CategoriesPost> categoriesPosts = categoriesPostService.findAll();
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Find all CategoryPost!")
                        .data(categoriesPosts)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Find All Category Post Active", description = "Find All Category Post Active")
    @GetMapping("/category-post/findAllActive")
    public ResponseEntity<MessageResponse> findAllActive(
            @RequestParam(required = false, defaultValue = "true") Boolean active) {
        log.info("Find All Category Post Active");
        Iterable<CategoriesPost> categoriesPosts = categoriesPostService.findByActive(active);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Find all CategoryPost Active!")
                        .data(categoriesPosts)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Find by category post", description = "Find by category post (Integer)")
    @GetMapping("/category-post/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable Integer id) {
        log.info("find by id Category {}", id);
        CategoriesPost categoriesPost = categoriesPostService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryPost not found!"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Get CategoryPost By Id!" + id)
                        .data(categoriesPost)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Find by category name", description = "Find by category name (String)")
    @GetMapping("/category-post/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@Valid @NotNull @PathVariable String name) {
        log.info("find by name Category {}", name);
        CategoriesPost categoriesPost = categoriesPostService
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryPost not found!"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Get CategoryPost By Name!" + name)
                        .data(categoriesPost)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Save category post", description = "Save new category post")
    @PostMapping("/category-post/save")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<MessageResponse> save(@Valid @RequestBody CategoriesPostRequest categoriesPostRequest) {
        log.info("save new Category post");
        CategoriesPost categoriesPost = new CategoriesPost();
        categoriesPost.setActive(true);
        categoriesPost.setLink(categoriesPostRequest.getLink());
        categoriesPost.setViews(1L);
        categoriesPost.setName(categoriesPostRequest.getName());
        categoriesPost.setAccount(accountServices
                .findById(categoriesPostRequest.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found!")));
        categoriesPostService.save(categoriesPost);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Save CategoryPost successfully!")
                        .data("success")
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Update category post", description = "Update category post by ID (Integer)")
    @PutMapping("/category-post/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<MessageResponse> update(
            @Valid @RequestBody CategoriesPostRequest categoriesPostRequest, @NotNull @PathVariable Integer id) {
        log.info("Update Category post ID {}", id);
        CategoriesPost categoriesPost = categoriesPostService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryPost not found!"));
        categoriesPost.setActive(categoriesPostRequest.getActive());
        categoriesPost.setLink(categoriesPostRequest.getLink());
        categoriesPost.setViews(categoriesPostRequest.getViews());
        categoriesPost.setName(categoriesPostRequest.getName());
        categoriesPost.setAccount(accountServices
                .findById(categoriesPostRequest.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found!")));
        categoriesPostService.update(categoriesPost);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Update CategoryPost Successfully!")
                        .data(id)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Delete category post", description = "Delete category post by ID (Integer)")
    @DeleteMapping("/category-post/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteByID(@Valid @NotNull @PathVariable Integer id) {
        log.info("Delete Category post ID {}", id);
        CategoriesPost categoriesPost = categoriesPostService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryPost not found!"));
        categoriesPost.setActive(false);
        categoriesPostService.update(categoriesPost);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Delete  CategoryPost By Id!" + id)
                        .data(id)
                        .build(),
                HttpStatus.OK);
    }
}
