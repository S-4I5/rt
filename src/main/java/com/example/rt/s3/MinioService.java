package com.example.rt.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final AmazonS3 minioClient;
    @Value("${s3.bucket-name}")
    private String bucketName;


    @SneakyThrows
    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + new Random().nextInt() + "_" + file.getOriginalFilename();

        minioClient.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
      
        return fileName;
    }

    @SneakyThrows
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));

        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException ignored) {}

        Files.delete(Path.of(Objects.requireNonNull(file.getOriginalFilename())));

        return convertedFile;
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = minioClient.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
