package com.trainingkaryawan.controller;

import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.request.DeleteRequest;
import com.trainingkaryawan.model.request.karyawan_training.KaryawanTrainingSaveRequest;
import com.trainingkaryawan.model.request.karyawan_training.KaryawanTrainingUpdateRequest;
import com.trainingkaryawan.model.response.GeneraleResponse;
import com.trainingkaryawan.service.impl.KaryawanTrainingServiceImpl;
import com.trainingkaryawan.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.trainingkaryawan.constant.GeneralConstant.*;

@Slf4j
@RestController
@RequestMapping("/karyawan-training")
public class KaryawanTrainingController implements CrudController<KaryawanTrainingSaveRequest, BasePagingRequest, KaryawanTrainingUpdateRequest, DeleteRequest, Long> {

    private final KaryawanTrainingServiceImpl karyawanTrainingService;

    public KaryawanTrainingController(KaryawanTrainingServiceImpl karyawanTrainingService) {
        this.karyawanTrainingService = karyawanTrainingService;
    }

    @PostMapping("/save")
    @Override
    public ResponseEntity<Object> create(@RequestBody KaryawanTrainingSaveRequest request) {
        log.info("incoming request save {} with request {}", KARYAWAN_TRAINING, JsonUtil.getString(request));
        Pair<HttpStatus, GeneraleResponse<Object>> response = karyawanTrainingService.save(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/list")
    @Override
    public ResponseEntity<Object> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        log.info("incoming request getAll {} with request page {} size {}", KARYAWAN_TRAINING, page, size);
        BasePagingRequest request = new BasePagingRequest(page, size);
        Pair<HttpStatus, GeneraleResponse<Object>> response = karyawanTrainingService.findAll(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Object> update(@RequestBody KaryawanTrainingUpdateRequest request) {
        log.info("incoming request update {} with request {}", KARYAWAN_TRAINING, JsonUtil.getString(request));
        Pair<HttpStatus, GeneraleResponse<Object>> response = karyawanTrainingService.update(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Object> delete(@RequestBody DeleteRequest request) {
        log.info("incoming request delete {} for id {}", KARYAWAN_TRAINING, request);
        Pair<HttpStatus, GeneraleResponse<Object>> response = karyawanTrainingService.delete(request.getId());
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@PathVariable(name = "id") Long request) {
        log.info("incoming request getById {} for id {}", KARYAWAN_TRAINING, request);
        Pair<HttpStatus, GeneraleResponse<Object>> response = karyawanTrainingService.findById(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }
}
