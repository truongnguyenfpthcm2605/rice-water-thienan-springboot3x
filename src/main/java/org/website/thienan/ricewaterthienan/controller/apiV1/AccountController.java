package org.website.thienan.ricewaterthienan.controller.apiv1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
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
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.RoleDetailService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UrlApi.API_V1)
public class AccountController {
    private final AccountServices accountServices;
    private final RoleDetailService roleDetailService;

    @DeleteMapping("/account/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> delete(@PathVariable String id) {
        Account account = accountServices.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
        account.setActive(false);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Delete account successfully")
                .data(accountServices.update(account).getId()).build(), HttpStatus.OK);
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
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Update account successfully")
                .timeStamp(LocalDateTime.now())
                .data(accountServices.update(account).getId())
                .build(), HttpStatus.OK);
    }

    @PatchMapping("/account/update_role/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> updateRole(@PathVariable String id, @RequestParam String role) {
        Account account = accountServices.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
        account.setRole(getRole(role));
        account.setUpdateAt(LocalDateTime.now());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Update role account successfully")
                .timeStamp(LocalDateTime.now())
                .data(accountServices.update(account).getId())
                .build(), HttpStatus.OK);
    }

    @PatchMapping("/account/update_role_detail/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> updateRoleDetail(@Valid @PathVariable String id, @NotEmpty @RequestParam("role-details") List<String> roleDetails) {
        Account account = accountServices.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Account id : " + id));
            account.setRoles(roleDetails.stream()
                    .map(e -> roleDetailService.findByName(e)
                            .orElseThrow( () -> new ResourceNotFoundException("Not found Role Name + "+ e ))).collect(Collectors.toSet()));
            account.setUpdateAt(LocalDateTime.now());
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Update Role Details successfully")
                    .timeStamp(LocalDateTime.now())
                    .data(accountServices.update(account).getId())
                    .build(), HttpStatus.OK);
    }

    private RoleEnum getRole(String role) {
        return switch (role) {
            case "Admin" -> RoleEnum.Admin;
            case "Staff" -> RoleEnum.Staff;
            default -> RoleEnum.User;
        };
    }


}
