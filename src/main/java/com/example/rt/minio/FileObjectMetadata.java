package com.example.rt.minio;

import lombok.Value;


@Value
public class FileObjectMetadata {
    String objectName;
    String contentType;
    long objectSize;
}
