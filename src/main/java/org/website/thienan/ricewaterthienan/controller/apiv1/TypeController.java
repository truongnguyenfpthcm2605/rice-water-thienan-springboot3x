package org.website.thienan.ricewaterthienan.controller.apiv1;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
@Tag(name = "Type Controller API")
@Slf4j
public class TypeController {
    private final TypeService typeRepository;
    private final AccountServices accountServices;
    private final PostService postService;

    @Operation(summary = "Find ALL Page Type", description = "Find All Page Type")
    @GetMapping("/type/findAll/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByActive(
            @Valid @RequestParam(required = false, defaultValue = "true") Boolean active,
            @PathVariable Optional<Integer> page,
            @NotNull @PathVariable String sort) {
        log.info("Find All Page Type");
        Page<Type> types = typeRepository.findByActive(
                SortAndPage.getPage(
                        page.orElse(0),
                        SortAndPage.MAX_PAGE,
                        sort.equals("up") ? SortAndPage.getSortUp("createAt") : SortAndPage.getSort("createAt")),
                active);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Get All Type")
                        .data(types)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Find ALL Page Type Title", description = "Find All Page Type Title")
    @GetMapping("/type/findAllTitle/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByTitle(
            @Valid @RequestParam(required = false, defaultValue = "true") Boolean active,
            @RequestParam String title,
            @PathVariable Optional<Integer> page,
            @NotNull @PathVariable String sort) {
        log.info("Find All Page Type Title");
        Page<Type> types = typeRepository.findByTitle(
                SortAndPage.getPage(
                        page.orElse(0),
                        SortAndPage.MAX_PAGE,
                        sort.equals("up") ? SortAndPage.getSortUp("createAt") : SortAndPage.getSort("createAt")),
                title,
                active);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Get All Type")
                        .data(types)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Find By Id Type", description = "Find By Id Type (Integer)")
    @GetMapping("/type/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable Integer id) {
        log.info("Find By Id Type");
        Type type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Type"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Get Type Success")
                        .data(type)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Save Type", description = "Save new type")
    @PostMapping("/type/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<MessageResponse> save(@Valid @RequestBody TypeRequest typeRequest) {
        log.info("Save type");
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Save Type Success")
                        .data(typeRepository
                                .save(getType(new Type(), typeRequest))
                                .getId())
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Update Type", description = "Update type")
    @PutMapping("/type/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<MessageResponse> update(
            @Valid @RequestBody TypeRequest typeRequest, @NotNull @PathVariable Integer id) {
        log.info("Update Type");
        Type type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found type"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Update Type Success")
                        .data(typeRepository.update(getType(type, typeRequest)))
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Delete Type", description = "Delete type")
    @DeleteMapping("/type/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<MessageResponse> delete(@Valid @NotNull @PathVariable Integer id) {
        Type type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found type"));
        type.setUpdateAt(LocalDateTime.now());
        type.setActive(false);
        typeRepository.save(type);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .timeStamp(LocalDateTime.now())
                        .message("Delete Post Success")
                        .data("Success")
                        .build(),
                HttpStatus.OK);
    }

    private Type getType(Type type, TypeRequest typeRequest) {
        type.setTitle(typeRequest.getTitle());
        type.setLink(typeRequest.getLink());
        type.setContent(typeRequest.getContent());
        type.setIntroduction(typeRequest.getIntroduction());
        type.setActive(typeRequest.getActive());
        type.setAvatar(typeRequest.getAvatar());
        type.setImageHeader(typeRequest.getImageHeader());
        type.setViews(typeRequest.getViews());
        type.setAccount(accountServices
                .findById(typeRequest.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found Account")));
        type.setPosts(typeRequest.getTypePost().stream()
                .map(e -> postService.findById(e).orElseThrow(() -> new ResourceNotFoundException("Not found post")))
                .collect(Collectors.toSet()));

        return type;
    }
}
