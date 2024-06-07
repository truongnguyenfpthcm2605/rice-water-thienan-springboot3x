package org.website.thienan.ricewaterthienan.controller.apiv1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;

import java.net.InetAddress;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
public class CommonController {

    @GetMapping("/index")
    public ResponseEntity<MessageResponse> index(){
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .message("Welcome to Thien An :)))")
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(){
        try {
            String computer = InetAddress.getLocalHost().getHostName();
            return new ResponseEntity<>("Stronger Server : " + computer, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
