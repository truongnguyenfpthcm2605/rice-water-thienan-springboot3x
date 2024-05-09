package org.website.thienan.ricewaterthienan.controller.apiV1.client;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.dto.response.AccountResponse;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.enums.RoleEnum;
import org.website.thienan.ricewaterthienan.security.jwt.JWTProvider;
import org.website.thienan.ricewaterthienan.security.userprincal.AccountService;
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.RoleDetailService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = UrlApi.API_V1)
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final AccountServices accountServices;
    private final RoleDetailService roleDetailService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @PostMapping("/auth/login")
    public ResponseEntity<MessageResponse> login(@Valid @RequestBody AccountRequest accountRequest, Errors errors){
        if(errors.hasErrors()){
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(405)
                    .message("Email and password is not empty")
                    .timeStamp(LocalDateTime.now())
                    .build()
                    , HttpStatus.BAD_REQUEST);
        }
       try {
           Authentication authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(accountRequest.getEmail(), accountRequest.getPassword())
           );
           SecurityContextHolder.getContext().setAuthentication(authentication);
           AccountService accountService = (AccountService) authentication.getPrincipal();
           String token = jwtProvider.generateToken(accountService);
           String refreshToken = jwtProvider.generateRefreshToken(new HashMap<>(), accountService);
           return new ResponseEntity<>(MessageResponse.builder()
                   .code(200)
                   .timeStamp(LocalDateTime.now())
                   .message("Login Successfully")
                   .data(AccountResponse.builder()
                           .email(accountService.getEmail())
                           .name(accountService.getName())
                           .avatar(accountService.getAvatar())
                           .token(token)
                           .refreshToken(refreshToken)
                           .timeToken("24H")
                           .authorities(accountService.getAuthorities())
                           .time(LocalDateTime.now())
                           .build()).build(),
                   HttpStatus.OK);
       }catch (Exception ex){
           return new ResponseEntity<>(MessageResponse.builder()
                   .code(500)
                   .message("Login Fail!")
                   .timeStamp(LocalDateTime.now()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
       }



    }

    @PostMapping("/auth/log-up")
    public ResponseEntity<MessageResponse> logUp(@Valid @RequestBody AccountRequest accountRequest, Errors errors){
        if(errors.hasErrors()){
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(405)
                    .message("Fields is valid")
                    .timeStamp(LocalDateTime.now())
                    .build()
                    , HttpStatus.BAD_REQUEST);
        }
        if(accountServices.findByEmailAndActive(accountRequest.getEmail(),true).isPresent()){
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(401)
                    .message("Account Email existed")
                    .timeStamp(LocalDateTime.now())
                    .build()
                    , HttpStatus.UNAUTHORIZED);
        }

        Account account = accountServices.save(account((accountRequest)));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("LogUp Successfully")
                .data(account.getId())
                .build(),
                HttpStatus.OK);
    }


    @PostMapping("/auth/logout/success")
    public  ResponseEntity<MessageResponse> logout(){
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200).timeStamp(LocalDateTime.now()).message("Logout successfully").build(),HttpStatus.OK);
    }


    private RoleEnum getRole(String role){
        return switch (role){
            case "Admin" -> RoleEnum.Admin;
            case "Staff" -> RoleEnum.Staff;
            default -> RoleEnum.User;
        };
    }

    private Account account(AccountRequest accountRequest){
        Account account =new Account();
        account.setName(accountRequest.getName());
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        account.setEmail(accountRequest.getEmail());
        account.setAvatar(accountRequest.getAvatar());
        account.setViews(1L);
        account.setActive(true);
        account.setRole(getRole(accountRequest.getRole()));
        account.setRoles(
                accountRequest.getRoleDetail().stream().map(
                        e -> roleDetailService.findByName(e).orElseThrow()
                ).collect(Collectors.toSet())
        );
        return  account;
    }
}
