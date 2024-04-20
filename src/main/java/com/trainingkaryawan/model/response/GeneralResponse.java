package com.trainingkaryawan.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse<E> {
    private int code;
    private String status;
    private MessageResponse message;
    private E data;
}
