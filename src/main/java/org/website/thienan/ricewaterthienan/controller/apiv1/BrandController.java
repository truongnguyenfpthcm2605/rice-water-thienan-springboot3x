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
@Tag(name = "Brand Controller API")
@Slf4j
public class BrandController {
    private final BrandService brandService;
    private final AccountServices accountServices;


    @Operation(summary = "Find All Brand", description = "Find All Brand")
    @GetMapping("/brand/findAll")
    public ResponseEntity<MessageResponse> findAll() {
        log.info("Find All Brand");
        Iterable<Brand> brands = brandService.findAll();
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Find all Brand!")
                .data(brands).build(), HttpStatus.OK);
    }

    @Operation(summary = "Find All Brand Active", description = "Find All Brand Active")
    @GetMapping("/brand/findAllActive")
    public ResponseEntity<MessageResponse> findAllActive(@RequestParam(defaultValue = "true", required = false) Boolean active) {
        log.info("Find All Brand Active");
        Iterable<Brand> brands = brandService.findByActive(active);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Find all Brand Active!")
                .data(brands).build(), HttpStatus.OK);
    }

    @Operation(summary = "Find By Id Brand", description = "Find By Id Brand (Integer)")
    @GetMapping("/brand/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable Integer id) {
        log.info("Find By Id {} Brand", id);
        Brand brand = brandService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found!"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get Brand By Id!" + id)
                .data(brand).build(), HttpStatus.OK);
    }

    @Operation(summary = "Find By Name Brand", description = "Find By Name Brand (String)")
    @GetMapping("/brand/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@Valid @NotNull @PathVariable String name) {
        log.info("Find By Name {} Brand", name);
        Brand brand = brandService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Brand not found!"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get Brand By Name!" + name)
                .data(brand).build(), HttpStatus.OK);
    }
    @Operation(summary = "Save brand", description = "Save new Brand")
    @PostMapping("/brand/save")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<MessageResponse> save(@Valid @RequestBody BrandRequest brandRequest) {
        log.info("Save brand {} name", brandRequest.getName());
        Brand brand = new Brand();
        brand.setActive(true);
        brand.setViews(brandRequest.getViews());
        brand.setName(brand.getName());
        brand.setAvatar(brandRequest.getAvatar());
        brand.setAccount(accountServices.findById(brandRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Account not found!")));
        brandService.save(brand);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Save Brand successfully!")
                .data("success").build(), HttpStatus.OK);
    }

    @Operation(summary = "Update brand", description = "Update Brand by ID")
    @PutMapping("/brand/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<MessageResponse> update(@Valid @RequestBody BrandRequest brandRequest,@NotNull @PathVariable Integer id) {
        log.info("Update brand {} id {}", brandRequest.getName(), id);
        Brand brand = brandService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found!"));
        brand.setActive(brandRequest.getActive());
        brand.setAvatar(brandRequest.getAvatar());
        brand.setViews(brandRequest.getViews());
        brand.setName(brandRequest.getName());
        brand.setUpdateAt(LocalDateTime.now());
        brand.setAccount(accountServices.findById(brandRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Account not found!")));
        brandService.update(brand);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Update Brand Successfully!")
                .data(id).build(), HttpStatus.OK);
    }

    @Operation(summary = "Delete brand", description = "Delete Brand by ID")
    @DeleteMapping("/brand/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteByID(@Valid @NotNull @PathVariable Integer id) {
        log.info("Delete brand id {}", id);
        Brand brand = brandService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found!"));
        brand.setActive(false);
        brand.setUpdateAt(LocalDateTime.now());
        brandService.update(brand);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Delete  Brand By Id!" + id)
                .data(id).build(), HttpStatus.OK);
    }
}
