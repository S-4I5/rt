package com.example.rt.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final AmazonS3 minioClient;

    @SneakyThrows
    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = 2 + "_" + file.getOriginalFilename();
        minioClient.putObject(new PutObjectRequest("somebucketname", fileName, fileObj));
        return fileName;
    }

    @SneakyThrows
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {}
        return convertedFile;
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = minioClient.getObject("somebucketname", fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
