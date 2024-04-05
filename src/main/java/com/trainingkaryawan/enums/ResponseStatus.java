package com.trainingkaryawan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseStatus {
    SUCCESS(HttpStatus.OK.value(), "SUCCESS"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "FAILED"),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "FAILED");

    private int code;
    private String message;
}
