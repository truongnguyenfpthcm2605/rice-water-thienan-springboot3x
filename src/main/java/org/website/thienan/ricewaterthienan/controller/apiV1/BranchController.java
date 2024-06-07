package org.website.thienan.ricewaterthienan.controller.apiv1;

import lombok.RequiredArgsConstructor;
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
public class BranchController {
    private final BranchService branchService;
    private final AccountServices accountServices;

    @GetMapping("/branch/findAll")
    public ResponseEntity<MessageResponse> findAll() {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Find all Branch!")
                .data(branchService.findAll()).build(), HttpStatus.OK);
    }

    @GetMapping("/branch/findAllActive")
    public ResponseEntity<MessageResponse> findAllActive(@RequestParam("active") Boolean active) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Find all Branch!")
                .data(branchService.findByActive(active)).build(), HttpStatus.OK);
    }


    @GetMapping("/branch/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@PathVariable("id") Integer id) {
        Branch branch = branchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found!"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get Branch By Id!"+ id)
                .data(branch).build(), HttpStatus.OK);
    }

    @GetMapping("/branch/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@PathVariable("name") String name) {
        Branch branch = branchService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Branch not found!"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get Branch By Name!"+ name)
                .data(branch).build(), HttpStatus.OK);
    }

    @PostMapping("/branch/save")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> save(@RequestBody BranchRequest branchRequest) {
        Branch branch = new Branch();
        branch.setActive(true);
        branch.setLink(branchRequest.getLink());
        branch.setViews(1L);
        branch.setName(branchRequest.getName());
        branch.setAccount(accountServices.findById(branchRequest.getAccountId()).orElseThrow());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Save Branch successfully!")
                .data(branchService.save(branch).getId()).build(), HttpStatus.OK);
    }

    @PutMapping("/branch/update/{id}")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> update( @RequestBody BranchRequest branchRequest ,@PathVariable("id") Integer id) {
        Branch branch = branchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found!"));
        if(branch!=null){
            branch.setActive(branchRequest.getActive());
            branch.setLink(branchRequest.getLink());
            branch.setViews(branchRequest.getViews());
            branch.setName(branchRequest.getName());
            branch.setUpdateAt(LocalDateTime.now());
            branch.setAccount(accountServices.findById(branchRequest.getAccountId()).orElseThrow());
        }
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Update Branch Successfully!")
                .data(branchService.update(branch).getId()).build(), HttpStatus.OK);
    }

    @PutMapping("/branch/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> deleteByID(@PathVariable("id") Integer id) {
        Branch branch = branchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found!"));
        branch.setActive(false);
        branch.setUpdateAt(LocalDateTime.now());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Delete  Branch By Id!"+ id)
                .data(branchService.update(branch).getId()).build(), HttpStatus.OK);
    }


}
