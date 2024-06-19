package org.website.thienan.ricewaterthienan.controller.apiv1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.request.CategoriesRequest;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.Categories;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.CategoriesService;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
@Tag(name = "Category Controller API")
@Slf4j
public class CategoriesController {

    private final CategoriesService categoriesService;
    private final AccountServices accountServices;

    @Operation(summary = "Find All Category", description = "Find All Category")
    @GetMapping("/categories/findAll")
    public ResponseEntity<MessageResponse> findAll() {
        log.info("Find All Category");  
        Iterable<Categories> categories = categoriesService.findAll();
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get All Categories")
                .timeStamp(LocalDateTime.now())
                .data(categories).build(), HttpStatus.OK);
    }
    @Operation(summary = "Find All Category Active", description = "Find All Category Active")
    @GetMapping("/categories/findAllActive")
    public ResponseEntity<MessageResponse> findAllActive(@RequestParam(required = false,defaultValue = "true") Boolean active) {
        log.info("Find All Category Active");
        Iterable<Categories> categories = categoriesService.findByActive(active);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get All Categories Active")
                .timeStamp(LocalDateTime.now())
                .data(categories).build(), HttpStatus.OK);
    }

    @Operation(summary = "Find By ID Category", description = "Find By ID Category (Integer) ")
    @GetMapping("/categories/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable Integer id) {
        log.info("Find by ID Category id {}", id);
        Categories categories = categoriesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Category ID" + id));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get Categories ID Success Full")
                .timeStamp(LocalDateTime.now())
                .data(categories)
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Find By Name Category", description = "Find By Name Category (String) ")
    @GetMapping("/categories/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@Valid @NotNull @PathVariable String name) {
        log.info("Find By Name Category {}", name);
        Categories categories = categoriesService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found Category ID" + name));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get Categories Name Success Full")
                .timeStamp(LocalDateTime.now())
                .data(categories)
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Save Category", description = "Save new Category ")
    @PostMapping("/categories/save")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<MessageResponse> save(@RequestBody CategoriesRequest categoriesRequest) {
        Categories categories = new Categories();
        categories.setName(categoriesRequest.getName());
        categories.setLink(categoriesRequest.getLink());
        categories.setContent(categoriesRequest.getContent());
        categories.setIntroduction(categoriesRequest.getIntroduction());
        categories.setAvatar(categoriesRequest.getAvatar());
        categories.setImageHeader(categoriesRequest.getImageHeader());
        categories.setViews(1L);
        categories.setActive(true);
        categories.setAccount(accountServices.findById(categoriesRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account ID" + categoriesRequest.getAccountId())));
        categoriesService.save(categories);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Categories save successfully")
                .data("success")
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Update Category", description = "Update Category by ID  ")
    @PutMapping("/categories/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<MessageResponse> update(@Valid @RequestBody CategoriesRequest categoriesRequest,@NotNull @PathVariable Integer id) {
        log.info("Update category by id {}", id);
        Categories categories = categoriesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categories Notfound ID" + id));
        categories.setName(categoriesRequest.getName());
        categories.setLink(categoriesRequest.getLink());
        categories.setContent(categoriesRequest.getContent());
        categories.setIntroduction(categoriesRequest.getIntroduction());
        categories.setAvatar(categoriesRequest.getAvatar());
        categories.setImageHeader(categoriesRequest.getImageHeader());
        categories.setViews(categoriesRequest.getViews());
        categories.setActive(categoriesRequest.getActive());
        categories.setAccount(accountServices.findById(categoriesRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account ID" + categoriesRequest.getAccountId())  ));
        categories.setUpdateAt(LocalDateTime.now());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Categories update successfully")
                .data(categoriesService.save(categories))
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Delete Category", description = "Delete Category by ID  ")
    @PutMapping("/categories/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> delete(@Valid @NotNull @PathVariable Integer id) {
        log.info("Delete category by id {}", id);
        Categories categories = categoriesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categories Notfound ID" + id));
        categories.setActive(false);
        categories.setUpdateAt(LocalDateTime.now());
        categoriesService.update(categories);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Categories delete successfully")
                .data(id)
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }
}
