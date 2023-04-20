package com.example.rt.minio;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final AmazonS3 minioClient;

    @SneakyThrows
    public void persistObject(@NonNull FileObject objectEntry) {
        minioClient.putObject("somebucketname",
                objectEntry.getObjectName(),
                new ByteArrayInputStream(objectEntry.getData()),
                map(objectEntry));
    }

    @SneakyThrows
    public void uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = 2 + "_" + file.getOriginalFilename();
        minioClient.putObject(new PutObjectRequest("somebucketname", fileName, fileObj));
    }

    @SneakyThrows
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {

        }
        return convertedFile;
    }

    @SneakyThrows
    public FileObject getFile(String name) {
        S3Object s3Object = minioClient.getObject("somebucketname", name);
        return map(name,
                IOUtils.toByteArray(s3Object.getObjectContent()),
                map(s3Object.getObjectMetadata()));
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = minioClient.getObject("somebucketname", fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static FileObject map(@NonNull String keyName, byte[] data, @NonNull FileObjectMetadata objectMetaDataProjection)  {
        return FileObject.builder()
                .id(Long.getLong(keyName))
                .data(data)
                .objectName(objectMetaDataProjection.getObjectName())
                .contentType(objectMetaDataProjection.getContentType())
                .objectSize(objectMetaDataProjection.getObjectSize())
                .build();
    }

    @SneakyThrows
    static ObjectMetadata map(@NonNull MultipartFile fileEntry) {
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileEntry.getBytes().length);
        objectMetadata.setContentType(fileEntry.getContentType());
        objectMetadata.addUserMetadata("filename", fileEntry.getOriginalFilename());
        return objectMetadata;
    }

    @SneakyThrows
    static ObjectMetadata map(@NonNull FileObject fileEntry) {
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileEntry.getData().length);
        objectMetadata.setContentType(fileEntry.getContentType());
        objectMetadata.addUserMetadata("filename", fileEntry.getObjectName());
        return objectMetadata;
    }

    static FileObjectMetadata map(ObjectMetadata s3ObjectMetaData) {
        return new FileObjectMetadata(
                s3ObjectMetaData.getUserMetadata().get("filename"),
                s3ObjectMetaData.getContentType(),
                s3ObjectMetaData.getContentLength());
    }
}
