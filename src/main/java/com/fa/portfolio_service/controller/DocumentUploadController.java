package com.fa.portfolio_service.controller;

import com.fa.portfolio_service.service.DocumentUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documents")
public class DocumentUploadController {

    private final DocumentUploadService documentUploadService;

    public DocumentUploadController(DocumentUploadService documentUploadService) {
        this.documentUploadService = documentUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("clientId") String clientId,
            @RequestParam("file") MultipartFile file
    ) {
        String s3Key = documentUploadService.uploadDocument(clientId, file);
        return ResponseEntity.ok("Document uploaded successfully: " + s3Key);
    }
}
