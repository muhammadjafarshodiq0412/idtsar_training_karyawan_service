package com.trainingkaryawan.controller.training;

import com.trainingkaryawan.controller.CrudController;
import com.trainingkaryawan.model.response.GeneraleResponse;
import com.trainingkaryawan.model.response.request.BasePagingRequest;
import com.trainingkaryawan.model.response.request.DeleteRequest;
import com.trainingkaryawan.model.response.request.master.TrainingSaveRequest;
import com.trainingkaryawan.model.response.request.master.TrainingUpdateRequest;
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

    private static final String CONTROLLER_NAME = "training";

    private final TrainingServiceImpl trainingService;

    public TrainingController(TrainingServiceImpl trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("/save")
    @Override
    public ResponseEntity<Object> create(@RequestBody TrainingSaveRequest request) {
        log.info("incoming request save {} with request {}", CONTROLLER_NAME, JsonUtil.getString(request));
        Pair<HttpStatus, GeneraleResponse<Object>> response = trainingService.save(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/list")
    @Override
    public ResponseEntity<Object> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        log.info("incoming request getAll {} with request page {} size {}", CONTROLLER_NAME, page, size);
        BasePagingRequest request = new BasePagingRequest(page, size);
        Pair<HttpStatus, GeneraleResponse<Object>> response = trainingService.findAll(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Object> update(@RequestBody TrainingUpdateRequest request) {
        log.info("incoming request update {} with request {}", CONTROLLER_NAME, JsonUtil.getString(request));
        Pair<HttpStatus, GeneraleResponse<Object>> response = trainingService.update(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Object> delete(@RequestBody DeleteRequest request) {
        log.info("incoming request delete {} for id {}", CONTROLLER_NAME, request);
        Pair<HttpStatus, GeneraleResponse<Object>> response = trainingService.delete(request.getId());
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@PathVariable(name = "id") Long request) {
        log.info("incoming request getById {} for id {}", CONTROLLER_NAME, request);
        Pair<HttpStatus, GeneraleResponse<Object>> response = trainingService.findById(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }
}
