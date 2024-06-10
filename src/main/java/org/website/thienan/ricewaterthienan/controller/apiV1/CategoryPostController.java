package org.website.thienan.ricewaterthienan.controller.apiV1;

import lombok.RequiredArgsConstructor;
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

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
public class CategoryPostController {
    private final CategoriesPostService categoriesPostService;
    private final AccountServices accountServices;

    @GetMapping("/category-post/findAll")
    public ResponseEntity<MessageResponse> findAll() {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Find all CategoryPost!")
                .data(categoriesPostService.findAll()).build(), HttpStatus.OK);
    }

    @GetMapping("/category-post/findAllActive")
    public ResponseEntity<MessageResponse> findAllActive(@RequestParam("active") Boolean active) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Find all CategoryPost!")
                .data(categoriesPostService.findByActive(active)).build(), HttpStatus.OK);
    }


    @GetMapping("/category-post/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@PathVariable("id") Integer id) {
        CategoriesPost categoriesPost = categoriesPostService.findById(id).orElseThrow(() -> new ResourceNotFoundException("CategoryPost not found!"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get CategoryPost By Id!" + id)
                .data(categoriesPost).build(), HttpStatus.OK);
    }

    @GetMapping("/category-post/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@PathVariable("name") String name) {
        CategoriesPost categoriesPost = categoriesPostService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("CategoryPost not found!"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get CategoryPost By Name!" + name)
                .data(categoriesPost).build(), HttpStatus.OK);
    }

    @PostMapping("/category-post/save")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> save(@RequestBody CategoriesPostRequest categoriesPostRequest) {
        CategoriesPost categoriesPost = new CategoriesPost();
        categoriesPost.setActive(true);
        categoriesPost.setLink(categoriesPostRequest.getLink());
        categoriesPost.setViews(1L);
        categoriesPost.setName(categoriesPostRequest.getName());
        categoriesPost.setAccount(accountServices.findById(categoriesPostRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Account not found!")));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Save CategoryPost successfully!")
                .data(categoriesPostService.save(categoriesPost).getId()).build(), HttpStatus.OK);
    }

    @PutMapping("/category-post/update/{id}")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> update(@RequestBody CategoriesPostRequest categoriesPostRequest, @PathVariable("id") Integer id) {
        CategoriesPost categoriesPost = categoriesPostService.findById(id).orElseThrow(() -> new ResourceNotFoundException("CategoryPost not found!"));
        categoriesPost.setActive(categoriesPostRequest.getActive());
        categoriesPost.setLink(categoriesPostRequest.getLink());
        categoriesPost.setViews(categoriesPostRequest.getViews());
        categoriesPost.setName(categoriesPostRequest.getName());
        categoriesPost.setUpdateAt(LocalDateTime.now());
        categoriesPost.setAccount(accountServices.findById(categoriesPostRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Account not found!")));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Update CategoryPost Successfully!")
                .data(categoriesPostService.update(categoriesPost).getId()).build(), HttpStatus.OK);
    }

    @PutMapping("/category-post/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> deleteByID(@PathVariable("id") Integer id) {
        CategoriesPost categoriesPost = categoriesPostService.findById(id).orElseThrow(() -> new ResourceNotFoundException("CategoryPost not found!"));
        categoriesPost.setActive(false);
        categoriesPost.setUpdateAt(LocalDateTime.now());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Delete  CategoryPost By Id!" + id)
                .data(categoriesPostService.update(categoriesPost).getId()).build(), HttpStatus.OK);
    }

}
