package com.trainingkaryawan.service;


import com.trainingkaryawan.model.request.BasePagingRequest;

public interface CrudService<S, U, R> {
    R save(S data);
    R update(U data);
    R delete(Long id);
    R findById(Long id);
    R findAll(BasePagingRequest request);
}
