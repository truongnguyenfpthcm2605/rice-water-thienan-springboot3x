package org.website.thienan.ricewaterthienan.controller.apiv1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.firebase.FirebaseStorageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlApi.API_V1)
@Tag(name = "Firebase Controller API")
@Slf4j
public class FileUploadController {

    private final FirebaseStorageService firebaseStorageService;

    @Operation(summary = "Upload Image Firebase", description = "Upload image in firebase  Cloud Storage")
    @PostMapping("/firebase/upload")
    public ResponseEntity<String> uploadFile(@Valid @NotNull @RequestParam MultipartFile file) throws IOException {
        log.info("Uploading image in firebase fileName {} ", file.getOriginalFilename());
        String fileUrl = firebaseStorageService.upload(file);
        return ResponseEntity.ok(fileUrl);

    }

    @Operation(summary = "Upload  Images Firebase", description = "Upload list image in firebase  Cloud Storage")
    @PostMapping("/firebase/uploads")
    public ResponseEntity<String> uploadFiles(@Valid @NotNull @RequestParam List<MultipartFile> files) throws IOException {
        StringBuilder str = new StringBuilder();
        log.info("Uploading image in firebase size list {} ", files.size());
        List<String> list = firebaseStorageService.uploadFile(files);
        if(!list.isEmpty()) {
            list.forEach(e -> str.append(e).append(","));
        }
        return ResponseEntity.ok(str.toString());

    }
}
