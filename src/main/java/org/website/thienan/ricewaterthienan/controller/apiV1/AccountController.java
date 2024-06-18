package org.website.thienan.ricewaterthienan.controller.apiv1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> delete(@Valid @NotNull @PathVariable String id) {
        log.info("Call API Delete Account by id {}", id);
        Account account = accountServices.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
        account.setActive(false);
        accountServices.update(account);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Delete account successfully")
                .data(id).build(), HttpStatus.OK);
    }

    @PutMapping("/account/update/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Staff','User')")
    public ResponseEntity<MessageResponse> update(
            @PathVariable String id, @Valid @RequestBody AccountRequest accountRequest) {
        Account account = accountServices.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
        account.setName(accountRequest.getName());
        account.setAvatar(accountRequest.getAvatar());
        account.setEmail(accountRequest.getEmail());
        account.setUpdateAt(LocalDateTime.now());
        accountServices.update(account);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Update account successfully")
                .timeStamp(LocalDateTime.now())
                .data(id)
                .build(), HttpStatus.OK);
    }

    @PatchMapping("/account/update_role/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> updateRole(
            @Valid @NotNull @PathVariable String id,
            @EnumPattern(name = "Role", regexp = "ADMIN|USER|STAFF") @RequestParam RoleEnum role) {
        Account account = accountServices.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
        account.setRole(role);
        account.setUpdateAt(LocalDateTime.now());
        accountServices.update(account);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Update role account successfully")
                .timeStamp(LocalDateTime.now())
                .data(id)
                .build(), HttpStatus.OK);
    }

    @PatchMapping("/account/update_role_detail/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> updateRoleDetail(@Valid @PathVariable String id, @NotEmpty @RequestParam("role-details") List<String> roleDetails) {
        Account account = accountServices.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
        account.setRoles(roleDetails.stream()
                .map(e -> roleDetailService.findByName(e)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Role Name + " + e))).collect(Collectors.toSet()));
        account.setUpdateAt(LocalDateTime.now());
        accountServices.update(account);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Update Role Details successfully")
                .timeStamp(LocalDateTime.now())
                .data(id)
                .build(), HttpStatus.OK);
    }


}
