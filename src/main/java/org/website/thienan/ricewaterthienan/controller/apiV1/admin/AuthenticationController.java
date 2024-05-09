package org.website.thienan.ricewaterthienan.controller.apiV1.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.dto.response.AccountResponse;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.security.jwt.JWTProvider;
import org.website.thienan.ricewaterthienan.security.userprincal.AccountService;

import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = UrlApi.API_V1)
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @PostMapping("/login")
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
}
