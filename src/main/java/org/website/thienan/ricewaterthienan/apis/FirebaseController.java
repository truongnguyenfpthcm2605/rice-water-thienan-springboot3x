package org.website.thienan.ricewaterthienan.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.firebase.IFirebaseStorageService;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/firebase")
@RequiredArgsConstructor
public class FirebaseController {

    private final IFirebaseStorageService firebaseStorageService;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String url = firebaseStorageService.upload(file);
        return new ResponseEntity<>(MessageResponse.builder()
                .data(url)
                .code(HttpStatus.OK.value())
                .message("Upload file firebase success")
                .timeStamp(LocalDateTime.now())
                .build(), HttpStatus.OK);
    }
}
