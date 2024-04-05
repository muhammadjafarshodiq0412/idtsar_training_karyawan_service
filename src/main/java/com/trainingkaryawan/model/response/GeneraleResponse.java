package com.trainingkaryawan.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneraleResponse<E> {

    private int code;
    private String status;
    private E data;

    public GeneraleResponse(int code, String status, E data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }
}
