package com.trainingkaryawan.service.impl;

import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.service.ResponseService;
import com.trainingkaryawan.util.FileUtil;
import com.trainingkaryawan.util.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.trainingkaryawan.constant.GeneralConstant.*;

@Slf4j
@Service
public class DocumentServiceImpl {

    @Value("${directory.file.upload}")
    private String directoryFileUpload;
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${server.domain}")
    private String serverAddress;
    private final ResponseService responseService;

    public DocumentServiceImpl(ResponseService responseService) {
        this.responseService = responseService;
    }

    public Pair<HttpStatus, GeneralResponse<Object>> uploadFile(MultipartFile multipartFile) {
        log.info(String.format(LOG_START, UPLOAD_FILE, ""));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_VALIDATE_OTP, null);
        try {
            File file = FileUtil.saveFile(directoryFileUpload, "", multipartFile);
            Map<String, Object> data = new HashMap<>();
            data.put("fileName", file.getName());
            data.put("fileDownloadUri", serverAddress.concat(":").concat(serverPort).concat(contextPath).concat("/show-file/").concat(file.getName()));
            data.put("fileType", FileUtil.getFileType(multipartFile));
            data.put("size", file.length());
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPLOAD_FILE, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, UPLOAD_FILE, "", e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, UPLOAD_FILE, ""));
        }
        return response;
    }

}
