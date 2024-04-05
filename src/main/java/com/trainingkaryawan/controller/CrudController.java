package com.trainingkaryawan.controller;

import org.springframework.http.ResponseEntity;

public interface CrudController<C, R, U, D, I> {

    ResponseEntity<Object> create(C request);

    ResponseEntity<Object> getAll(Integer page, Integer size);

    ResponseEntity<Object> update(U request);

    ResponseEntity<Object> delete(D request);

    ResponseEntity<Object> getById(I request);
}
