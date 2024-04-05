package com.trainingkaryawan.model.response.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePagingRequest {
    private Integer page;
    private Integer size;
}
