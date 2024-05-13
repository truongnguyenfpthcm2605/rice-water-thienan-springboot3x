package org.website.thienan.ricewaterthienan.controller.apiV1;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Get;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class PostController {

    private final PostService postService;
    private final AccountServices accountServices;
    private final CategoriesService categoriesService;
    private final CategoriesPostService categoriesPostService;

    @GetMapping("/post/findAll/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByActive(
            @RequestParam("active") Boolean active,
            @PathVariable("page") Optional<Integer> page,
            @PathVariable("sort") String sort) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get All Post")
                .data(
                        postService.findByActive(
                                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                                        sort.equals("up") ? SortAndPage.getSortUp("createAt") :
                                                SortAndPage.getSort("createAt")), active
                        )
                ).build(), HttpStatus.OK);
    }

    @GetMapping("/post/findAll/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByTitle(
            @RequestParam("active") Boolean active,
            @RequestParam("title") String title,
            @PathVariable("page") Optional<Integer> page,
            @PathVariable("sort") String sort) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get All Post")
                .data(
                        postService.findByTitle(
                                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                                        sort.equals("up") ? SortAndPage.getSortUp("createAt") :
                                                SortAndPage.getSort("createAt")),title, active
                        )
                ).build(), HttpStatus.OK);
    }

    @GetMapping("/post/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@PathVariable("id") Integer id){
        Post post = postService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Post"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .timeStamp(LocalDateTime.now())
                        .message("Get Post Success")
                        .data(post).build(),HttpStatus.OK
        );
    }

    @PostMapping("/post/save")
    public ResponseEntity<MessageResponse> save(@RequestBody PostRequest postRequest){
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setLink(postRequest.getLink());
        post.setContent(postRequest.getContent());
        post.setIntroduction(postRequest.getIntroduction());
        post.setActive(true);
        post.setAvatar(postRequest.getAvatar());
        post.setImageHeader(postRequest.getImageHeader());
        post.setViews(1L);
        post.setAccount(accountServices.findById(postRequest.getAccountId()).orElseThrow());
        post.setCategoryPost(categoriesPostService.findById(postRequest.getCategoriesPostId()).orElseThrow());
        post.setCategories(postRequest.getCategories().stream().map(e -> categoriesService.findById(e).orElseThrow()).collect(Collectors.toSet()));

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .timeStamp(LocalDateTime.now())
                        .message("Save Post Success")
                        .data(postService.save(post).getId()).build(),HttpStatus.OK
        );

    }

    @PutMapping("/post/update/{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody PostRequest postRequest, @PathVariable("id") Integer id){
        Post post = postService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found post"));
        if(post!=null){
            post.setLink(postRequest.getLink());
            post.setUpdateAt(LocalDateTime.now());
            post.setContent(postRequest.getContent());
            post.setIntroduction(postRequest.getIntroduction());
            post.setActive(postRequest.getActive());
            post.setAvatar(postRequest.getAvatar());
            post.setImageHeader(postRequest.getImageHeader());
            post.setViews(postRequest.getViews());
            post.setAccount(accountServices.findById(postRequest.getAccountId()).orElseThrow());
            post.setCategoryPost(categoriesPostService.findById(postRequest.getCategoriesPostId()).orElseThrow());
            post.setCategories(postRequest.getCategories().stream().map(e -> categoriesService.findById(e).orElseThrow()).collect(Collectors.toSet()));

        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .timeStamp(LocalDateTime.now())
                        .message("Update Post Success")
                        .data(postService.update(post).getId()).build(),HttpStatus.OK
        );
    }

    @PutMapping("/post/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Integer id){
        Post post = postService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found post"));
        if(post!=null){
            post.setUpdateAt(LocalDateTime.now());
            post.setActive(false);
        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .timeStamp(LocalDateTime.now())
                        .message("Delete Post Success")
                        .data(postService.update(post).getId()).build(),HttpStatus.OK
        );
    }





}
