package org.website.thienan.ricewaterthienan.firebase;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IFirebaseStorageService {
    String upload(MultipartFile file) throws IOException;

    List<String> uploadFile(List<MultipartFile> files) throws IOException;
}
