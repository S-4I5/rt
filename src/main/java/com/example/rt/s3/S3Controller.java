package com.example.rt.s3;

import com.example.rt.s3.responces.FilePostResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/files")
public class S3Controller {
    private final MinioService minioService;

    @PostMapping()
    public ResponseEntity<FilePostResponse> post(
            @RequestParam(value = "file") MultipartFile file
    ) {
        return ResponseEntity.ok(new FilePostResponse(minioService.uploadFile(file)));
    }

    @GetMapping("{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = minioService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}