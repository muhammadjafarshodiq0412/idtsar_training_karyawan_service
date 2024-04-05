package com.trainingkaryawan.service.impl;

import static com.trainingkaryawan.constant.GeneralConstant.*;

import com.trainingkaryawan.entity.TrainingEntity;
import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.response.GeneraleResponse;
import com.trainingkaryawan.model.response.request.BasePagingRequest;
import com.trainingkaryawan.model.response.request.master.TrainingSaveRequest;
import com.trainingkaryawan.model.response.request.master.TrainingUpdateRequest;
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
    private static final String entityName = "training";
    private final TrainingRepository trainingRepository;
    private final ResponseService responseService;

    public TrainingServiceImpl(TrainingRepository trainingRepository, ResponseService responseService) {
        this.trainingRepository = trainingRepository;
        this.responseService = responseService;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> save(TrainingSaveRequest data) {
        log.info(String.format(LOG_START, SAVE, entityName));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_SAVING, null);
        TrainingEntity entity = new TrainingEntity(data);
        try {
            entity = trainingRepository.save(entity);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_SAVE, entity);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, SAVE, entityName, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, SAVE, entityName));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> update(TrainingUpdateRequest data) {
        log.info(String.format(LOG_START, UPDATE, entityName));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_UPDATING, null);
        TrainingEntity entity = trainingRepository.findById(data.getId()).orElse(null);
        try {
            if (entity == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, entityName));
                response = responseService.generateErrorDataNotFound();
            } else {
                entity.setPengajar(data.getPengajar());
                entity.setTema(data.getTema());
                entity = trainingRepository.save(entity);
                response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, entity);
            }
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, UPDATE, entityName, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, UPDATE, entityName));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> delete(Long id) {
        log.info(String.format(LOG_START, DELETE, entityName));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_DELETING, null);
        TrainingEntity data = trainingRepository.findById(id).orElse(null);
        try {
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, entityName));
                response = responseService.generateErrorDataNotFound();
            } else {
                trainingRepository.delete(data);
                response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, null);
            }
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, DELETE, entityName, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, DELETE, entityName));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> findById(Long id) {
        log.info(String.format(LOG_START, GET_BY_ID, entityName));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        TrainingEntity data = null;
        try {
            data = trainingRepository.findById(id).orElse(null);
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, entityName));
                response = responseService.generateErrorDataNotFound();
            }
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_BY_ID, entityName, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_BY_ID, entityName));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> findAll(BasePagingRequest request) {
        log.info(String.format(LOG_START, GET_ALL, entityName));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        Page<TrainingEntity> data;
        try {
            data = trainingRepository.findAll(pageRequest);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_ALL, entityName, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_ALL, entityName));
        }
        return response;
    }
}
