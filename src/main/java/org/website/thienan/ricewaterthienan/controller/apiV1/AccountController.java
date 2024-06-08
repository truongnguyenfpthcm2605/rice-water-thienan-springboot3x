package org.website.thienan.ricewaterthienan.controller.apiV1;

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

    @PutMapping("/account/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") String id) {
        Account account = accountServices.findById(id).orElse(null);
        if (account != null) {
            account.setActive(false);
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(200)
                    .timeStamp(LocalDateTime.now())
                    .message("Delete account successfully")
                    .data(accountServices.update(account).getId()).build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(MessageResponse.builder()
                .code(404).message("Not found Account ID :" + id)
                .timeStamp(LocalDateTime.now()).build(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/account/update/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Staff','User')")
    public ResponseEntity<MessageResponse> update(
            @PathVariable("id") String id, @RequestBody AccountRequest accountRequest) {
        Account account = accountServices.findById(id).orElse(null);
        if (account != null) {
            account.setName(accountRequest.getName());
            account.setAvatar(accountRequest.getAvatar());
            account.setEmail(accountRequest.getEmail());
            account.setUpdateAt(LocalDateTime.now());
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(200)
                    .message("Update account successfully")
                    .timeStamp(LocalDateTime.now())
                    .data(accountServices.update(account).getId())
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(MessageResponse.builder()
                .code(404)
                .message("Not found Account ID :" + id)
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/account/update_role/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> updateRole(@PathVariable("id") String id, @RequestParam("role") String role) {
        Account account = accountServices.findById(id).orElse(null);
        if (account != null) {
            account.setRole(getRole(role));
            account.setUpdateAt(LocalDateTime.now());
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(200)
                    .message("Update account successfully")
                    .timeStamp(LocalDateTime.now())
                    .data(accountServices.update(account).getId())
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(MessageResponse.builder()
                .code(404)
                .message("Not found Account ID :" + id)
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/account/update_role_detail/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> updateRoleDetail(@PathVariable("id") String id, @RequestParam("role-details") List<String> roleDetails) {
        Account account = accountServices.findById(id).orElse(null);
        if (account != null) {
            account.setRoles(roleDetails.stream().map(e -> roleDetailService.findByName(e).orElseThrow()).collect(Collectors.toSet()));
            account.setUpdateAt(LocalDateTime.now());
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(200)
                    .message("Update Role Details successfully")
                    .timeStamp(LocalDateTime.now())
                    .data(accountServices.update(account).getId())
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(MessageResponse.builder()
                .code(404)
                .message("Not found Account ID :" + id)
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }


    private RoleEnum getRole(String role) {
        return switch (role) {
            case "Admin" -> RoleEnum.Admin;
            case "Staff" -> RoleEnum.Staff;
            default -> RoleEnum.User;
        };
    }


}
