package com.trainingkaryawan.controller;

import static com.trainingkaryawan.constant.GeneralConstant.*;

import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.request.DeleteRequest;
import com.trainingkaryawan.model.request.training.TrainingSaveRequest;
import com.trainingkaryawan.model.request.training.TrainingUpdateRequest;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.service.impl.TrainingServiceImpl;
import com.trainingkaryawan.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/training")
public class TrainingController implements CrudController<TrainingSaveRequest, BasePagingRequest, TrainingUpdateRequest, DeleteRequest, Long> {

    private final TrainingServiceImpl trainingService;

    public TrainingController(TrainingServiceImpl trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("/save")
    @Override
    public ResponseEntity<Object> create(@RequestBody TrainingSaveRequest request) {
        log.info("incoming request save {} with request {}", TRAINING, JsonUtil.getString(request));
        Pair<HttpStatus, GeneralResponse<Object>> response = trainingService.save(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/list")
    @Override
    public ResponseEntity<Object> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        log.info("incoming request getAll {} with request page {} size {}", TRAINING, page, size);
        BasePagingRequest request = new BasePagingRequest(page, size);
        Pair<HttpStatus, GeneralResponse<Object>> response = trainingService.findAll(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Object> update(@RequestBody TrainingUpdateRequest request) {
        log.info("incoming request update {} with request {}", TRAINING, JsonUtil.getString(request));
        Pair<HttpStatus, GeneralResponse<Object>> response = trainingService.update(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Object> delete(@RequestBody DeleteRequest request) {
        log.info("incoming request delete {} for id {}", TRAINING, request);
        Pair<HttpStatus, GeneralResponse<Object>> response = trainingService.delete(request.getId());
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@PathVariable(name = "id") Long request) {
        log.info("incoming request getById {} for id {}", TRAINING, request);
        Pair<HttpStatus, GeneralResponse<Object>> response = trainingService.findById(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }
}
