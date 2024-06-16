package org.website.thienan.ricewaterthienan.controller.apiV1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.request.TypeRequest;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.Type;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.PostService;
import org.website.thienan.ricewaterthienan.services.TypeService;
import org.website.thienan.ricewaterthienan.utils.SortAndPage;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
public class TypeController {
    private final TypeService typeRepository;
    private final AccountServices accountServices;
    private final PostService postService;


    @GetMapping("/type/findAll/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByActive(
            @RequestParam("active") Boolean active,
            @PathVariable("page") Optional<Integer> page,
            @PathVariable("sort") String sort) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get All Type")
                .data(
                        typeRepository.findByActive(
                                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                                        sort.equals("up") ? SortAndPage.getSortUp("createAt") :
                                                SortAndPage.getSort("createAt")), active
                        )
                ).build(), HttpStatus.OK);
    }

    @GetMapping("/type/findAllTitle/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByTitle(
            @RequestParam("active") Boolean active,
            @RequestParam("title") String title,
            @PathVariable("page") Optional<Integer> page,
            @PathVariable("sort") String sort) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get All Type")
                .data(
                        typeRepository.findByTitle(
                                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                                        sort.equals("up") ? SortAndPage.getSortUp("createAt") :
                                                SortAndPage.getSort("createAt")),title, active
                        )
                ).build(), HttpStatus.OK);
    }

    @GetMapping("/type/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@PathVariable("id") Integer id){
        Type type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Type"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .timeStamp(LocalDateTime.now())
                        .message("Get Type Success")
                        .data(type).build(),HttpStatus.OK
        );
    }

    @PostMapping("/type/save")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> save(@Valid @RequestBody TypeRequest typeRequest){
        Type type = new Type();
        type.setTitle(typeRequest.getTitle());
        type.setLink(typeRequest.getLink());
        type.setContent(typeRequest.getContent());
        type.setIntroduction(typeRequest.getIntroduction());
        type.setActive(true);
        type.setAvatar(typeRequest.getAvatar());
        type.setImageHeader(typeRequest.getImageHeader());
        type.setViews(1L);
        type.setAccount(accountServices.findById(typeRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")));
        type.setPosts(typeRequest.getTypePost().stream()
                .map(e -> postService.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not found post"))).collect(Collectors.toSet()));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .timeStamp(LocalDateTime.now())
                        .message("Save Type Success")
                        .data(typeRepository.save(type).getId()).build(),HttpStatus.OK
        );

    }

    @PutMapping("/type/update/{id}")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> update(@RequestBody TypeRequest typeRequest, @PathVariable("id") Integer id){
        Type type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found type"));
        if(type!=null){
            type.setTitle(typeRequest.getTitle());
            type.setLink(typeRequest.getLink());
            type.setContent(typeRequest.getContent());
            type.setIntroduction(typeRequest.getIntroduction());
            type.setActive(typeRequest.getActive());
            type.setAvatar(typeRequest.getAvatar());
            type.setImageHeader(typeRequest.getImageHeader());
            type.setViews(typeRequest.getViews());
            type.setAccount(accountServices.findById(typeRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account")));
            type.setPosts(typeRequest.getTypePost().stream()
                    .map(e -> postService.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not found Post"))).collect(Collectors.toSet()));
        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .timeStamp(LocalDateTime.now())
                        .message("Update Type Success")
                        .data(typeRepository.update(type).getId()).build(),HttpStatus.OK
        );
    }

    @PutMapping("/type/delete/{id}")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Integer id){
        Type type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found type"));
            type.setUpdateAt(LocalDateTime.now());
            type.setActive(false);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .timeStamp(LocalDateTime.now())
                        .message("Delete Post Success")
                        .data(typeRepository.save(type).getId()).build(),HttpStatus.OK
        );
    }

}
