package com.example.rt.minio;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileObject {
    @Id
    @GeneratedValue
    private Long id;
    private String objectName;
    private String contentType;
    private long objectSize;
    private byte[] data;
}
