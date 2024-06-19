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
import org.website.thienan.ricewaterthienan.dto.request.BranchRequest;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.Branch;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.BranchService;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
@Tag(name = "Branch Controller API")
@Slf4j
public class BranchController {
    private final BranchService branchService;
    private final AccountServices accountServices;

    @Operation(summary = "Find All Branch", description = "Find All Branch")
    @GetMapping("/branch/findAll")
    public ResponseEntity<MessageResponse> findAll() {
        log.info("Find All Branch");
        Iterable<Branch> branches = branchService.findAll();
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Find all Branch!")
                .data(branches).build(), HttpStatus.OK);
    }

    @Operation(summary = "Find All Branch Active", description = "Find All Branch active")
    @GetMapping("/branch/findAllActive")
    public ResponseEntity<MessageResponse> findAllActive(@RequestParam(defaultValue = "true", required = false) boolean active) {
        log.info("Find All Branch Active");
        Iterable<Branch> branches = branchService.findByActive(active);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Find all Branch!")
                .data(branches).build(), HttpStatus.OK);
    }


    @Operation(summary = "Find by Id Branch", description = "Find By Id Branch (Integer)")
    @GetMapping("/branch/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable Integer id) {
        log.info("Find By Id Branch {}",id);
        Branch branch = branchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found!"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get Branch By Id!" + id)
                .data(branch).build(), HttpStatus.OK);
    }

    @Operation(summary = "Find by Name Branch", description = "Find By Name Branch (String)")
    @GetMapping("/branch/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@Valid @NotNull @PathVariable String name) {
        log.info("Find By Name Branch {}",name);
        Branch branch = branchService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Branch not found!"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get Branch By Name!" + name)
                .data(branch).build(), HttpStatus.OK);
    }

    @Operation(summary = "Save Branch", description = "Save New Branch ")
    @PostMapping("/branch/save")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<MessageResponse> save(@Valid @RequestBody BranchRequest branchRequest) {
        Branch branch = new Branch();
        branch.setActive(true);
        branch.setLink(branchRequest.getLink());
        branch.setViews(branchRequest.getViews());
        branch.setName(branchRequest.getName());
        branch.setAccount(accountServices.findById(branchRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Account not found!")));
        branchService.save(branch);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Save Branch successfully!")
                .data("success").build(), HttpStatus.OK);
    }

    @Operation(summary = "Update Branch", description = "Update Branch by Id (Integer) ")
    @PutMapping("/branch/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<MessageResponse> update(@Valid @RequestBody BranchRequest branchRequest,@NotNull @PathVariable Integer id) {
        log.info("Update Branch {}",id);
        Branch branch = branchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found!"));
        branch.setActive(branchRequest.getActive());
        branch.setLink(branchRequest.getLink());
        branch.setViews(branchRequest.getViews());
        branch.setName(branchRequest.getName());
        branch.setUpdateAt(LocalDateTime.now());
        branch.setAccount(accountServices.findById(branchRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Account not found!")));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Update Branch Successfully!")
                .data(branchService.update(branch).getId()).build(), HttpStatus.OK);
    }

    @Operation(summary = "Delete Branch", description = "Delete Branch by Id (Integer) ")
    @DeleteMapping("/branch/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteByID(@Valid @NotNull @PathVariable Integer id) {
        log.info("Delete Branch by Id {}",id);
        Branch branch = branchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found!"));
        branch.setActive(false);
        branch.setUpdateAt(LocalDateTime.now());
        branchService.update(branch);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Delete  Branch By Id!" + id)
                .data(id).build(), HttpStatus.OK);
    }


}
