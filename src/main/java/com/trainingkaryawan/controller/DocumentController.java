package com.trainingkaryawan.controller;

import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.service.impl.DocumentServiceImpl;
import com.trainingkaryawan.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.trainingkaryawan.constant.GeneralConstant.KARYAWAN;


@Slf4j
@RestController
@RequestMapping("/document")
public class DocumentController {
    private final DocumentServiceImpl documentService;

    public DocumentController(DocumentServiceImpl documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload-file")
    public ResponseEntity<Object> uploadFile(@RequestPart MultipartFile file) {
        log.info("incoming request upload file with size {} and file name {}", KARYAWAN, file.getSize(), file.getName());
        Pair<HttpStatus, GeneralResponse<Object>> response = documentService.uploadFile(file);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

}
