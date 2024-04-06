package com.trainingkaryawan.controller;

import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.request.DeleteRequest;
import com.trainingkaryawan.model.request.karyawan.KaryawanSaveRequest;
import com.trainingkaryawan.model.request.karyawan.KaryawanUpdateRequest;
import com.trainingkaryawan.model.response.GeneraleResponse;
import com.trainingkaryawan.service.impl.KaryawanServiceImpl;
import com.trainingkaryawan.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.trainingkaryawan.constant.GeneralConstant.KARYAWAN;

@Slf4j
@RestController
@RequestMapping("/karyawan")
public class KaryawanController implements CrudController<KaryawanSaveRequest, BasePagingRequest, KaryawanUpdateRequest, DeleteRequest, Long> {

    private final KaryawanServiceImpl karyawanService;

    @Autowired
    public KaryawanController(KaryawanServiceImpl karyawanService) {
        this.karyawanService = karyawanService;
    }

    @PostMapping("/save")
    @Override
    public ResponseEntity<Object> create(@RequestBody KaryawanSaveRequest request) {
        log.info("incoming request save {} with request {}", KARYAWAN, JsonUtil.getString(request));
        Pair<HttpStatus, GeneraleResponse<Object>> response = karyawanService.save(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/list")
    @Override
    public ResponseEntity<Object> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        log.info("incoming request getAll {} with request page {} size {}", KARYAWAN, page, size);
        BasePagingRequest request = new BasePagingRequest(page, size);
        Pair<HttpStatus, GeneraleResponse<Object>> response = karyawanService.findAll(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Object> update(@RequestBody KaryawanUpdateRequest request) {
        log.info("incoming request update {} with request {}", KARYAWAN, JsonUtil.getString(request));
        Pair<HttpStatus, GeneraleResponse<Object>> response = karyawanService.update(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Object> delete(@RequestBody DeleteRequest request) {
        log.info("incoming request delete {} for id {}", KARYAWAN, request);
        Pair<HttpStatus, GeneraleResponse<Object>> response = karyawanService.delete(request.getId());
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id) {
        log.info("incoming request getById {} for id {}", KARYAWAN, id);
        Pair<HttpStatus, GeneraleResponse<Object>> response = karyawanService.findById(id);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }
}
