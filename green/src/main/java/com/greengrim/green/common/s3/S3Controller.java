package com.greengrim.green.common.s3;

import com.greengrim.green.common.s3.dto.S3Result;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @Operation(summary = "S3에 파일 업로드하기")
    @PostMapping("/file")
    public ResponseEntity<S3Result> uploadFiles(
            @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok(s3Service.uploadFile(multipartFile));
    }
}
