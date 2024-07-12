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
import org.website.thienan.ricewaterthienan.dto.request.PostRequest;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.Post;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.CategoriesPostService;
import org.website.thienan.ricewaterthienan.services.CategoriesService;
import org.website.thienan.ricewaterthienan.services.PostService;
import org.website.thienan.ricewaterthienan.utils.SortAndPage;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
@Tag(name = "Post Controller API")
@Slf4j
public class PostController {

    private final PostService postService;
    private final AccountServices accountServices;
    private final CategoriesService categoriesService;
    private final CategoriesPostService categoriesPostService;

    @Operation(summary = "Find All Post Active", description = "Find All Post Active")
    @GetMapping("/post/findAll/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByActive(
            @Valid @NotNull @RequestParam(defaultValue = "true", required = false) Boolean active,
            @NotNull @PathVariable Optional<Integer> page,
            @NotNull @PathVariable String sort) {
        log.info("Find All Post Active");
        Page<Post> postPage = postService.findByActive(
                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                        sort.equals("up") ? SortAndPage.getSortUp("createAt") :
                                SortAndPage.getSort("createAt")), active
        );
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get All Post")
                .data(postPage).build(), HttpStatus.OK);
    }

    @Operation(summary = "Find All Post Title", description = "Find All Post Title")
    @GetMapping("/post/findAllTitle/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByTitle(
            @Valid @NotNull @RequestParam(defaultValue = "true", required = false) Boolean active,
            @NotNull @PathVariable Optional<Integer> page,
            @NotNull @RequestParam String title,
            @NotNull @PathVariable String sort) {
        log.info("Find All Post Title");
        Page<Post> postPage = postService.findByTitle(
                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                        sort.equals("up") ? SortAndPage.getSortUp("createAt") :
                                SortAndPage.getSort("createAt")), title, active
        );
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get All Post")
                .data(postPage).build(), HttpStatus.OK);
    }

    @Operation(summary = "Find Post By ID", description = "Find Post By ID")
    @GetMapping("/post/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable Integer id) {
        Post post = postService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Post"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Get Post Success")
                        .data(post).build(), HttpStatus.OK);
    }

    @Operation(summary = "Save Post", description = "Save new post")
    @PostMapping("/post/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF','USER')")
    public ResponseEntity<MessageResponse> save(@Valid @RequestBody PostRequest postRequest) {
        log.info("Save Post");
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Save Post Success")
                        .data(postService.save(getPost(new Post(),postRequest)).getId()).build(), HttpStatus.OK);

    }

    @Operation(summary = "Update Post", description = "update post by Id (Integer")
    @PutMapping("/post/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF','USER')")
    public ResponseEntity<MessageResponse> update(@Valid @RequestBody PostRequest postRequest, @NotNull @PathVariable Integer id) {
        log.info("Update post");
        Post post = postService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found post"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Update Post Success")
                        .data(postService.update(getPost(post,postRequest)).getId()).build(), HttpStatus.OK);
    }

    @Operation(summary = "Delete Post", description = "Delete post by Id (Integer")
    @DeleteMapping("/post/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF','USER')")
    public ResponseEntity<MessageResponse> delete(@Valid @NotNull @PathVariable Integer id) {
        Post post = postService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found post"));
        post.setActive(false);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Delete Post Success")
                        .data(postService.update(post).getId()).build(), HttpStatus.OK);
    }

    private  Post getPost(Post post,PostRequest postRequest) {
        post.setTitle(postRequest.getTitle());
        post.setLink(postRequest.getLink());
        post.setContent(postRequest.getContent());
        post.setIntroduction(postRequest.getIntroduction());
        post.setActive(postRequest.getActive());
        post.setAvatar(postRequest.getAvatar());
        post.setImageHeader(postRequest.getImageHeader());
        post.setViews(postRequest.getViews());
        post.setAccount(accountServices.findById(postRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")));
        post.setCategoryPost(categoriesPostService.findById(postRequest.getCategoriesPostId()).orElseThrow(() -> new ResourceNotFoundException("Not found Category Post")));
        post.setCategories(postRequest.getCategories().stream()
                .map(e -> categoriesService.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not Found Category ID"))).collect(Collectors.toSet()));
        return  post;
    }


}
