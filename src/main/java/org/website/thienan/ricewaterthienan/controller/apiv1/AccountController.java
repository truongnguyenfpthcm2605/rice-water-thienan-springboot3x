package org.website.thienan.ricewaterthienan.controller.apiv1;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.enums.RoleEnum;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.exceptions.customValidation.EnumPattern;
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.RoleDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UrlApi.API_V1)
@Tag(name = "Account Controller API")
@Slf4j
public class AccountController {
    private final AccountServices accountServices;
    private final RoleDetailService roleDetailService;

    @Operation(summary = "Delete Account", description = "Delete Account By ID")
    @DeleteMapping("/account/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> delete(@Valid @NotNull @PathVariable String id) {
        log.info("Call API Delete Account by id {}", id);
        Account account = accountServices
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
        account.setActive(false);
        accountServices.update(account);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .message("Delete account successfully")
                        .data(id)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Update account", description = "Update account by id")
    @PutMapping("/account/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF','USER')")
    public ResponseEntity<MessageResponse> update(
            @Valid @NotNull @PathVariable String id, @RequestBody AccountRequest accountRequest) {
        log.info("Call API Update Account by id {}", id);
        Account account = accountServices
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
        account.setName(accountRequest.getName());
        account.setAvatar(accountRequest.getAvatar());
        account.setEmail(accountRequest.getEmail());
        accountServices.update(account);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Update account successfully")
                        .timeStamp(LocalDateTime.now())
                        .data(id)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Update permission account", description = "Update permission account by id")
    @PatchMapping("/account/update_role/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> updateRole(
            @Valid @NotNull @PathVariable String id,
            @EnumPattern(name = "Role", regexp = "ADMIN|USER|STAFF") @RequestParam RoleEnum role) {
        log.info("Call API Update permission Account by id {}", id);
        Account account = accountServices
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
        account.setRole(role);
        accountServices.update(account);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Update role account successfully")
                        .timeStamp(LocalDateTime.now())
                        .data(id)
                        .build(),
                HttpStatus.OK);
    }

    @Operation(summary = "Update role-detail account", description = "Update role-detail account by id")
    @PatchMapping("/account/update_role_detail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> updateRoleDetail(
            @Valid @NotNull @PathVariable String id, @NotEmpty @RequestParam("role-details") List<String> roleDetails) {
        log.info("Call API Update role-detail Account by id {}", id);
        Account account = accountServices
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
        account.setRoles(roleDetails.stream()
                .map(e -> roleDetailService
                        .findByName(e)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Role Name + " + e)))
                .collect(Collectors.toSet()));
        accountServices.update(account);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Update Role Details successfully")
                        .timeStamp(LocalDateTime.now())
                        .data(id)
                        .build(),
                HttpStatus.OK);
    }
}
