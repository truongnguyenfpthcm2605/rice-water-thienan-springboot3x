package org.website.thienan.ricewaterthienan.controller.apiv1;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.dto.response.AccountResponse;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.messages.mail.MailService;
import org.website.thienan.ricewaterthienan.security.jwt.JWTProvider;
import org.website.thienan.ricewaterthienan.security.userprincal.AccountDetailService;
import org.website.thienan.ricewaterthienan.security.userprincal.AccountService;
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.RoleDetailService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UrlApi.API_V1)
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final AccountServices accountServices;
    private final RoleDetailService roleDetailService;
    private final MailService mailService;
    private final AccountDetailService accountDetailService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @PostMapping("/auth/login")
    public ResponseEntity<MessageResponse> login(@Valid @RequestBody AccountRequest accountRequest, Errors errors) {
        if (errors.hasErrors()) {
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
        } catch (Exception ex) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(500)
                    .message("Login Fail!")
                    .timeStamp(LocalDateTime.now()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/log-up")
    public ResponseEntity<MessageResponse> logUp(@Valid @RequestBody AccountRequest accountRequest, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(405)
                    .message("Fields is valid")
                    .timeStamp(LocalDateTime.now())
                    .build()
                    , HttpStatus.BAD_REQUEST);
        }
        if (accountServices.findByEmailAndActive(accountRequest.getEmail(), true).isPresent()) {
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

    @PostMapping("/auth/change-password")
    @PreAuthorize("hasAnyRole('Admin', 'Staff','User')")
    public ResponseEntity<MessageResponse> changePassword(@RequestParam("email") String email,
                                                          @RequestParam("newPassword") String newPassword) {
        Account account = accountServices.findByEmailAndActive(email, true).orElse(null);
        if (account != null) {
            if (passwordEncoder.matches(newPassword, account.getPassword())) {
                account.setPassword(passwordEncoder.encode(newPassword));
                Account account1 = accountServices.update(account);
                return new ResponseEntity<>(MessageResponse.builder()
                        .code(200).message("Change Password successfully")
                        .data(account1.getId())
                        .timeStamp(LocalDateTime.now()).build(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(MessageResponse.builder()
                .code(405).message("Old password valid")
                .timeStamp(LocalDateTime.now()).build(), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/auth/remember_password")
    @PreAuthorize("hasAnyRole('Admin', 'Staff','User')")
    public ResponseEntity<MessageResponse> remember(@RequestParam("email") String email) throws MessagingException {
        Account account = accountServices.findByEmailAndActive(email, true).orElseThrow(() -> new ResourceNotFoundException("Not found Account with email :" + email));
        String refreshPassword = randomCodeMail();
        mailService.send(email, "Mail xác thực tài khoản từ Gạo Và Nước Thiên An",
                "  <div style=width:80%; margin:0 auto;text-align: center ;>\n" +
                        "    <h1 style=color:#080202 ;>Gạo Nước Thiên An</h1>\n" +
                        "    <p>Dùng mã này để xác minh địa chỉ email của bạn trên Gạo Và Nước Thiên An </p>\n" +
                        "    <p>Xin chào Bạn,Chúng tôi cần xác minh địa chỉ email của bạn để đảm bảo là có thể liên hệ với bạn sau khi xem xét\n" +
                        "      ID.</p>\n" +
                        "    <p>Chúng tôi cần xác minh địa chỉ email của bạn để đảm bảo là có thể liên hệ với bạn sau khi xem xét ID.</p>\n" +
                        "    <h5>Mã xác nhận</h5>" +
                        "<h2 style=color: #116D6E;>" + refreshPassword + "</h2>" +
                        "      <br>" +
                        "    <p style=font-size: 15px;font-weight: 200;>Tin nhắn này được gửi tới bạn theo yêu cầu của Travel Bee.\n" +
                        "      Gạo Và Nước Thiên An © 2024 All rights . Privacy Policy|T&C|System Status</p>\n" +
                        "  </div>");
        account.setPassword(passwordEncoder.encode(refreshPassword));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Send refresh Password for Mail success full")
                .data(accountServices.update(account).getId()).build(), HttpStatus.OK);
    }


    @PostMapping("/auth/logout/success")
    public ResponseEntity<MessageResponse> logout() {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200).timeStamp(LocalDateTime.now()).message("Logout successfully").build(), HttpStatus.OK);
    }

    @GetMapping("/auth/denied")
    public ResponseEntity<MessageResponse> accessDenied() {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(401).timeStamp(LocalDateTime.now()).message("Access Denied , You Login , Please !").build(), HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("/auth/refresh-token")
    public ResponseEntity<AccountResponse> refreshToken(@RequestParam("re-fresh-token") String refreshToken) {
        try {
            String email = jwtProvider.extractUsername(refreshToken);
            Account account = accountServices.findByEmailAndActive(email, true)
                    .orElseThrow(() -> new RuntimeException("Account not found or not active"));

            UserDetails userDetails = accountDetailService.loadUserByUsername(account.getEmail());

            if (jwtProvider.isTokenValid(refreshToken, userDetails)) {
                String newToken = jwtProvider.generateToken(userDetails);
                String newRefreshToken = jwtProvider.generateRefreshToken(new HashMap<>(), userDetails);

                AccountResponse accountResponse = new AccountResponse();
                accountResponse.setToken(newToken);
                accountResponse.setRefreshToken(newRefreshToken);
                accountResponse.setTime(LocalDateTime.now());

                return ResponseEntity.ok(accountResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }




    private Account account(AccountRequest accountRequest) {
        Account account = new Account();
        account.setName(accountRequest.getName());
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        account.setEmail(accountRequest.getEmail());
        account.setAvatar(accountRequest.getAvatar());
        account.setViews(1L);
        account.setActive(true);
        account.setRole(accountRequest.getRoleEnum());
        account.setRoles(
                accountRequest.getRoleDetail().stream().map(
                        e -> roleDetailService.findByName(e).orElseThrow()
                ).collect(Collectors.toSet())
        );
        return account;
    }

    private static String randomCodeMail() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
