package com.trainingkaryawan.controller;

import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.request.DeleteRequest;
import com.trainingkaryawan.model.request.rekening.RekeningSaveRequest;
import com.trainingkaryawan.model.request.rekening.RekeningUpdateRequest;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.service.impl.RekeningServiceImpl;
import com.trainingkaryawan.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.trainingkaryawan.constant.GeneralConstant.REKENING;

@Slf4j
@RestController
@RequestMapping("/rekening")
public class RekeningController implements CrudController<RekeningSaveRequest, BasePagingRequest, RekeningUpdateRequest, DeleteRequest, Long> {

    private final RekeningServiceImpl rekeningService;

    public RekeningController(RekeningServiceImpl rekeningService) {
        this.rekeningService = rekeningService;
    }

    @PostMapping("/save")
    @Override
    public ResponseEntity<Object> create(@RequestBody RekeningSaveRequest request) {
        log.info("incoming request save {} with request {}", REKENING, JsonUtil.getString(request));
        Pair<HttpStatus, GeneralResponse<Object>> response = rekeningService.save(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/list")
    @Override
    public ResponseEntity<Object> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        log.info("incoming request getAll {} with request page {} size {}", REKENING, page, size);
        BasePagingRequest request = new BasePagingRequest(page, size);
        Pair<HttpStatus, GeneralResponse<Object>> response = rekeningService.findAll(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Object> update(@RequestBody RekeningUpdateRequest request) {
        log.info("incoming request update {} with request {}", REKENING, JsonUtil.getString(request));
        Pair<HttpStatus, GeneralResponse<Object>> response = rekeningService.update(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Object> delete(@RequestBody DeleteRequest request) {
        log.info("incoming request delete {} for id {}", REKENING, request);
        Pair<HttpStatus, GeneralResponse<Object>> response = rekeningService.delete(request.getId());
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@PathVariable(name = "id") Long request) {
        log.info("incoming request getById {} for id {}", REKENING, request);
        Pair<HttpStatus, GeneralResponse<Object>> response = rekeningService.findById(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }
}
