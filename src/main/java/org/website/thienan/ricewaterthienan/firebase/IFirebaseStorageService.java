package org.website.thienan.ricewaterthienan.firebase;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFirebaseStorageService {
    String uploadFile(MultipartFile file) throws IOException;
}
