package org.website.thienan.ricewaterthienan.firebase;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IFirebaseStorageService {
    String upload(MultipartFile file) throws IOException;
    List<String> uploadFile(List<MultipartFile> files) throws IOException;
}
