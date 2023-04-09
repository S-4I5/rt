package com.example.rt.minio;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final AmazonS3 minioClient;

    @SneakyThrows
    public void uploadFile(FileObject file) {
        minioClient.putObject("somebucketname",
                file.getObjectName(),
                new ByteArrayInputStream(file.getData()),
                map(file));
    }

    @SneakyThrows
    public FileObject getFile(String name) {
        S3Object s3Object = minioClient.getObject("somebucketname", name);
        return map(name,
                s3Object.getObjectContent().readAllBytes(),
                map(s3Object.getObjectMetadata()));
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
