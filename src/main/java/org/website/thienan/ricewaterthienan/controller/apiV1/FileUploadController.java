package org.website.thienan.ricewaterthienan.controller.apiV1;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.firebase.FirebaseStorageService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlApi.API_V1)
public class FileUploadController {

    private final FirebaseStorageService firebaseStorageService;

    @PostMapping("/firebase/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = firebaseStorageService.uploadFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
