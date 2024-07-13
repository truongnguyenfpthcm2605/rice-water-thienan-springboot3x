package org.website.thienan.ricewaterthienan.controller.apiv1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.website.thienan.ricewaterthienan.config.languageConfig.Translator;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;

import java.net.InetAddress;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
@Tag(name = "Welcome to Project")
public class CommonController {

    private final Translator translator;

    @Operation(summary = "Welcome to Website", description = "Welcome to Thien An Website")
    @GetMapping("/index")
    public ResponseEntity<MessageResponse> index(){
        return new ResponseEntity<>(
                MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .message(translator.toLocale("welcome"))
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Check health Server", description = "Check health Server")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() throws Exception{
            String computer = InetAddress.getLocalHost().getHostName();
            return new ResponseEntity<>("Stronger Server : " + computer, HttpStatus.OK);
    }



}
