package com.trainingkaryawan.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtil {

    private FileUtil() {
        throw new IllegalStateException("ConvertUtil class");
    }

    public static File saveFile(String directory, String filename, MultipartFile file) {
        log.info("start convert to file");
        String[] fileNameAndExtension = getFileNameAndExtension(file);
        File tempFile = new File(directory.concat(ObjectUtils.isEmpty(filename) ? fileNameAndExtension[0] : filename).concat(".").concat(fileNameAndExtension[1]));

        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
            outputStream.write(file.getBytes());
        } catch (IOException e) {
            tempFile.deleteOnExit();
            log.error("error message {}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (tempFile.isFile()) {
                log.info("tempFile created to {}", tempFile.getAbsolutePath());
            }
        }
        log.info("end convert to file process");
        return tempFile;
    }

    public static String[] getFileNameAndExtension(MultipartFile file) {
        String[] result = new String[2];

        if (file.isEmpty()) {
            return result;
        }
        // Get the original filename from the MultipartFile object
        String originalFilename = file.getOriginalFilename();
        if (ObjectUtils.isEmpty(originalFilename)) {
            return result;
        }
        // Find the last occurrence of '.' to separate the extension
        int lastDotIndex = originalFilename.lastIndexOf('.');
        if (lastDotIndex != -1 && lastDotIndex < originalFilename.length() - 1) {
            // Extract the filename (substring before the last dot)
            result[0] = originalFilename.substring(0, lastDotIndex);
            // Extract the extension (substring after the last dot)
            result[1] = originalFilename.substring(lastDotIndex + 1);
        } else {
            // No extension found
            result[0] = originalFilename;
            result[1] = "";
        }

        return result;
    }

    public static void deleteFileIfExist(String filePath, String extension) throws IOException {
        Path path = Paths.get(filePath.concat(extension));
        log.info("start delete file with path {}", path);
        boolean isDeleted = Files.deleteIfExists(path);
        if (isDeleted) {
            log.info("success delete file");
        } else {
            log.info("failed delete file doesn't exist");
        }
    }

    public static String getFileType(MultipartFile file) {
        String[] fileNameAndExtension = getFileNameAndExtension(file);
        String fileType = null;
        if (!ObjectUtils.isEmpty(fileNameAndExtension[1])) {
            switch (fileNameAndExtension[1]) {
                case "jpeg":
                    fileType = "image/jpeg,";
                case "jpg":
                    fileType = "image/jpg,";
                    break;
                case "png":
                    fileType = "image/png";
                    break;
                case "pdf":
                    fileType = "application/pdf";
                    break;
                default:
                    return null;
            }
        }
        return fileType;
    }

}
