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
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.RoleDetail;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.RoleDetailService;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
@Tag(name = "Role Detail Controller API")
@Slf4j
public class RoleDetailController {
    private final RoleDetailService roleDetailService;

    @Operation(summary = "Role Detail Find All" , description = "Role Detail Find All")
    @GetMapping("/role_detail/findAll")
    public ResponseEntity<MessageResponse> findAll(){
        log.info("Find All Role Details");
        Iterable<RoleDetail> roleDetails = roleDetailService.findAll();
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Get All Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetails).build(), HttpStatus.OK);
    }


    @Operation(summary = "Role Detail Find All Active" , description = "Role Detail Find All Active")
    @GetMapping("/role_detail/findAllActive")
    public ResponseEntity<MessageResponse> findAll(@RequestParam(required = false,defaultValue = "true") Boolean active){
        log.info("Find All Role Details Active");
        Iterable<RoleDetail> roleDetails = roleDetailService.findByActive(active);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Get All Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetails).build(),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Role Detail Find By Id" , description = "Role Detail Find By Id")
    @GetMapping("/role_detail/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable Integer id){
        log.info("Find By Id Role Detail");
        RoleDetail roleDetail = roleDetailService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found Role Detail"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Get Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetail).build(), HttpStatus.OK);

    }

    @Operation(summary = "Role Detail Find By Name" , description = "Role Detail Find By Name")
    @GetMapping("/role_detail/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@Valid @NotNull @PathVariable String name){
        log.info("Find By Name Role Detail");
        RoleDetail roleDetail = roleDetailService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not Found Role Detail"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Get Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetail).build(), HttpStatus.OK);

    }

    @Operation(summary = "Save Role_Detail" , description = "Save Role Detail")
    @PostMapping("/role_detail/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> save(@Valid @NotNull @RequestParam String name){
        log.info("Save Role Detail");
        RoleDetail roleDetail = new RoleDetail();
        roleDetail.setName(name);
        roleDetail.setActive(true);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Save Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetailService.save(roleDetail).getName()).build(),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update Role_Detail" , description = "Update Role Detail")
    @PutMapping("/role_detail/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> update(
           @Valid @NotNull @PathVariable Integer id,
           @NotNull @RequestParam String name,
           @RequestParam(required = false,defaultValue = "true") Boolean active){
        log.info("Update Role Detail");
        RoleDetail roleDetail = roleDetailService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Role Detail"));
        roleDetail.setName(name);
        roleDetail.setActive(active);
        roleDetail.setUpdateAt(LocalDateTime.now());
        roleDetailService.save(roleDetail);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Update Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(id).build(), HttpStatus.OK);
    }

}
