package org.website.thienan.ricewaterthienan.controller.apiV1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.request.BrandRequest;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.Brand;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.BrandService;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final AccountServices accountServices;

    @GetMapping("/brand/findAll")
    public ResponseEntity<MessageResponse> findAll() {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Find all Brand!")
                .data(brandService.findAll()).build(), HttpStatus.OK);
    }

    @GetMapping("/brand/findAllActive")
    public ResponseEntity<MessageResponse> findAllActive(@RequestParam(defaultValue = "true", required = false) Boolean active) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Find all Brand Active!")
                .data(brandService.findByActive(active)).build(), HttpStatus.OK);
    }


    @GetMapping("/brand/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable Integer id) {
        Brand brand = brandService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found!"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get Brand By Id!" + id)
                .data(brand).build(), HttpStatus.OK);
    }

    @GetMapping("/brand/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@Valid @NotNull @PathVariable String name) {
        Brand brand = brandService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Brand not found!"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get Brand By Name!" + name)
                .data(brand).build(), HttpStatus.OK);
    }

    @PostMapping("/brand/save")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> save(@Valid @RequestBody BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setActive(true);
        brand.setViews(brandRequest.getViews());
        brand.setName(brand.getName());
        brand.setAvatar(brandRequest.getAvatar());
        brand.setAccount(accountServices.findById(brandRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Account not found!")));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Save Brand successfully!")
                .data(brandService.save(brand).getId()).build(), HttpStatus.OK);
    }

    @PutMapping("/brand/update/{id}")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> update(@Valid @RequestBody BrandRequest brandRequest,@NotNull @PathVariable Integer id) {
        Brand brand = brandService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found!"));
        brand.setActive(brandRequest.getActive());
        brand.setAvatar(brandRequest.getAvatar());
        brand.setViews(brandRequest.getViews());
        brand.setName(brandRequest.getName());
        brand.setUpdateAt(LocalDateTime.now());
        brand.setAccount(accountServices.findById(brandRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Account not found!")));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Update Brand Successfully!")
                .data(brandService.update(brand).getId()).build(), HttpStatus.OK);
    }

    @DeleteMapping("/brand/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> deleteByID(@Valid @NotNull @PathVariable Integer id) {
        Brand brand = brandService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found!"));
        brand.setActive(false);
        brand.setUpdateAt(LocalDateTime.now());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Delete  Brand By Id!" + id)
                .data(brandService.update(brand).getId()).build(), HttpStatus.OK);
    }
}
