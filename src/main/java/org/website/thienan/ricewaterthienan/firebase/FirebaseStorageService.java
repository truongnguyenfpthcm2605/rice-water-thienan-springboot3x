package org.website.thienan.ricewaterthienan.firebase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FirebaseStorageService implements IFirebaseStorageService {

    @Value("${bucketname.firebase}")
    private String bucketName;

    @Value("${URLupload.firebase}")
    private String baseUrl;

    private Storage storage;

    @PostConstruct
    public void init() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("firebase-service-account.json");
        assert inputStream != null;
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        storage =
                StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(baseUrl, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    @Override
    public List<String> uploadFile(List<MultipartFile> files) throws IOException {
        List<String> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            fileName = UUID.randomUUID().toString().concat(getExtension(fileName));
            File tempFile = convertToFile(file, fileName);
            String url = uploadFile(tempFile, fileName);
            fileList.add(url);
            tempFile.delete();
        }
        return fileList;
    }

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        fileName = UUID.randomUUID().toString().concat(getExtension(fileName));
        File tempFile = convertToFile(multipartFile, fileName);
        String url = uploadFile(tempFile, fileName);
        tempFile.delete();
        return url;
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(
                System.getProperty("java.io.tmpdir"),
                fileName); // Tạo file tạm thời trong thư mục tạm thời của hệ thống thay vì tạo file trong thư mục hiện
        // tại của ứng dụng
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
