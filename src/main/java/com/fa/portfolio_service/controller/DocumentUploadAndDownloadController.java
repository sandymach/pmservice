package com.fa.portfolio_service.controller;

import com.fa.portfolio_service.service.DocumentUploadAndDownloadService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documents")
public class DocumentUploadAndDownloadController {

    private final DocumentUploadAndDownloadService documentService;

    public DocumentUploadAndDownloadController(DocumentUploadAndDownloadService documentUploadService) {
        this.documentService = documentUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("clientId") String clientId,
            @RequestParam("file") MultipartFile file
    ) {
        String s3Key = documentService.uploadDocument(clientId, file);
        return ResponseEntity.ok("Document uploaded successfully: " + s3Key);
    }
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(
            @RequestParam String bucket,
            @RequestParam String key) {

        byte[] file = documentService.downloadFile(bucket, key);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=document.pdf")
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }
}
