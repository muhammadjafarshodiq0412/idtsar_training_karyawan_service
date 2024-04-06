package com.trainingkaryawan.service.impl;

import static com.trainingkaryawan.constant.GeneralConstant.*;

import com.trainingkaryawan.entity.TrainingEntity;
import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.response.GeneraleResponse;
import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.request.training.TrainingSaveRequest;
import com.trainingkaryawan.model.request.training.TrainingUpdateRequest;
import com.trainingkaryawan.repository.TrainingRepository;
import com.trainingkaryawan.service.CrudService;
import com.trainingkaryawan.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrainingServiceImpl implements CrudService<TrainingSaveRequest, TrainingUpdateRequest, Pair<HttpStatus, GeneraleResponse<Object>>> {
    private final TrainingRepository trainingRepository;
    private final ResponseService responseService;

    public TrainingServiceImpl(TrainingRepository trainingRepository, ResponseService responseService) {
        this.trainingRepository = trainingRepository;
        this.responseService = responseService;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> save(TrainingSaveRequest data) {
        log.info(String.format(LOG_START, SAVE, TRAINING));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_SAVING, null);
        TrainingEntity entity = new TrainingEntity(data);
        try {
            entity = trainingRepository.save(entity);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_SAVE, entity);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, SAVE, TRAINING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, SAVE, TRAINING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> update(TrainingUpdateRequest data) {
        log.info(String.format(LOG_START, UPDATE, TRAINING));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_UPDATING, null);
        TrainingEntity entity = trainingRepository.findById(data.getId()).orElse(null);
        try {
            if (entity == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, TRAINING));
                response = responseService.generateErrorDataNotFound();
            } else {
                entity.setPengajar(data.getPengajar());
                entity.setTema(data.getTema());
                entity = trainingRepository.save(entity);
                response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, entity);
            }
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, UPDATE, TRAINING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, UPDATE, TRAINING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> delete(Long id) {
        log.info(String.format(LOG_START, DELETE, TRAINING));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_DELETING, null);
        TrainingEntity data = trainingRepository.findById(id).orElse(null);
        try {
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, TRAINING));
                response = responseService.generateErrorDataNotFound();
            } else {
                trainingRepository.delete(data);
                response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, null);
            }
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, DELETE, TRAINING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, DELETE, TRAINING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> findById(Long id) {
        log.info(String.format(LOG_START, GET_BY_ID, TRAINING));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        try {
            TrainingEntity data = trainingRepository.findById(id).orElse(null);
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, TRAINING));
                response = responseService.generateErrorDataNotFound();
            }
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_BY_ID, TRAINING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_BY_ID, TRAINING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> findAll(BasePagingRequest request) {
        log.info(String.format(LOG_START, GET_ALL, TRAINING));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        try {
            Page<TrainingEntity> data = trainingRepository.findAll(pageRequest);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_ALL, TRAINING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_ALL, TRAINING));
        }
        return response;
    }
}
