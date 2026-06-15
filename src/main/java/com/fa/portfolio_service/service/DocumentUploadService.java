package com.fa.portfolio_service.service;

import com.fa.portfolio_service.aws.s3.S3Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DocumentUploadService {

    private S3Client s3Client;

    @Value("${aws.s3.document-bucket}")
    private String documentBucket;

    public DocumentUploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadDocument(String clientId, MultipartFile file){
        validateFile(file);
        String s3Key =  generateKey(clientId,file.getOriginalFilename());
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(documentBucket)
                .key(s3Key)
                .contentType(file.getContentType())
                .build();

        try {
            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(file.getBytes())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  s3Key;
    }

    private void validateFile(MultipartFile file){
        if(file.isEmpty()){
            throw  new RuntimeException("Empty files can not be processed");
        } else if (!"application/pdf".equals(file.getContentType())) {
            throw  new RuntimeException("Only pdf files can process");
        }
    }

    private String generateKey(String clientId,String originalFileName){
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return  "clients/" + clientId + "/documents/" + timeStamp + "-" + originalFileName;
    }
}
