package org.website.thienan.ricewaterthienan.controller.apiv1;

import lombok.RequiredArgsConstructor;
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
public class CategoriesController {

    private final CategoriesService categoriesService;
    private final AccountServices accountServices;

    @GetMapping("/categories/findAll")
    public ResponseEntity<MessageResponse> findAll(){
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .message("Get All Categories")
                .timeStamp(LocalDateTime.now())
                .data(categoriesService.findAll()).build(), HttpStatus.OK);
    }

    @GetMapping("/categories/findAllActive")
    public ResponseEntity<MessageResponse> findAllActive(@RequestParam("active") Boolean active){
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .message("Get All Categories Active")
                .timeStamp(LocalDateTime.now())
                .data(categoriesService.findByActive(active)).build(), HttpStatus.OK);
    }

    @GetMapping("/categories/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@PathVariable("id") Integer id){
        Categories categories = categoriesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Category ID"+ id));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .message("Get Categories ID Success Full")
                .timeStamp(LocalDateTime.now())
                .data(categories)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/categories/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@PathVariable("name") String name){
        Categories categories = categoriesService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found Category ID"+ name));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .message("Get Categories Name Success Full")
                .timeStamp(LocalDateTime.now())
                .data(categories)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/categories/save")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> save(@RequestBody CategoriesRequest categoriesRequest){
        Categories categories = new Categories();
        categories.setName(categoriesRequest.getName());
        categories.setLink(categoriesRequest.getLink());
        categories.setContent(categoriesRequest.getContent());
        categories.setIntroduction(categoriesRequest.getIntroduction());
        categories.setAvatar(categoriesRequest.getAvatar());
        categories.setImageHeader(categoriesRequest.getImageHeader());
        categories.setViews(1L);
        categories.setActive(true);
        categories.setAccount(accountServices.findById(categoriesRequest.getAccountId()).orElseThrow());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .message("Categories save successfully")
                .data(categoriesService.save(categories))
                .timeStamp(LocalDateTime.now())
                .build(),HttpStatus.OK);
    }

    @PostMapping("/categories/update/{id}")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> update(@RequestBody CategoriesRequest categoriesRequest,@PathVariable("id") Integer id){
        Categories categories = categoriesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categories Notfound ID"+ id));
        if(categories!=null){
            categories.setName(categoriesRequest.getName());
            categories.setLink(categoriesRequest.getLink());
            categories.setContent(categoriesRequest.getContent());
            categories.setIntroduction(categoriesRequest.getIntroduction());
            categories.setAvatar(categoriesRequest.getAvatar());
            categories.setImageHeader(categoriesRequest.getImageHeader());
            categories.setViews(1L);
            categories.setActive(true);
            categories.setAccount(accountServices.findById(categoriesRequest.getAccountId()).orElseThrow());
            categories.setUpdateAt(LocalDateTime.now());
        }
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .message("Categories update successfully")
                .data(categoriesService.save(categories))
                .timeStamp(LocalDateTime.now())
                .build(),HttpStatus.OK);
    }

    @PutMapping("/categories/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Integer id){
        Categories categories = categoriesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categories Notfound ID"+ id));
        categories.setActive(false);
        categories.setUpdateAt(LocalDateTime.now());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .message("Categories delete successfully")
                .data(categoriesService.update(categories).getId())
                .timeStamp(LocalDateTime.now())
                .build(),HttpStatus.OK);
    }
}
