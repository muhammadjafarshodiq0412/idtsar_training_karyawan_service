package com.trainingkaryawan.service.impl;

import com.trainingkaryawan.constant.GeneralConstant;
import com.trainingkaryawan.entity.KaryawanEntity;
import com.trainingkaryawan.entity.KaryawanTrainingEntity;
import com.trainingkaryawan.entity.TrainingEntity;
import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.request.karyawan_training.KaryawanTrainingSaveRequest;
import com.trainingkaryawan.model.request.karyawan_training.KaryawanTrainingUpdateRequest;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.repository.KaryawanRepository;
import com.trainingkaryawan.repository.KaryawanTrainingRepository;
import com.trainingkaryawan.repository.TrainingRepository;
import com.trainingkaryawan.service.CrudService;
import com.trainingkaryawan.service.ResponseService;
import com.trainingkaryawan.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.trainingkaryawan.constant.GeneralConstant.*;

@Slf4j
@Service
public class KaryawanTrainingServiceImpl implements CrudService<KaryawanTrainingSaveRequest, KaryawanTrainingUpdateRequest, Pair<HttpStatus, GeneralResponse<Object>>> {
    private final KaryawanRepository karyawanRepository;
    private final TrainingRepository trainingRepository;
    private final KaryawanTrainingRepository karyawanTrainingRepository;
    private final ResponseService responseService;

    @Autowired
    public KaryawanTrainingServiceImpl(KaryawanRepository karyawanRepository, TrainingRepository trainingRepository, KaryawanTrainingRepository karyawanTrainingRepository, ResponseService responseService) {
        this.karyawanRepository = karyawanRepository;
        this.trainingRepository = trainingRepository;
        this.karyawanTrainingRepository = karyawanTrainingRepository;
        this.responseService = responseService;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> save(KaryawanTrainingSaveRequest data) {
        log.info(String.format(LOG_START, SAVE, KARYAWAN_TRAINING));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_SAVING, null);
        KaryawanTrainingEntity karyawanTraining = new KaryawanTrainingEntity(data);
        try {
            KaryawanEntity karyawan = karyawanRepository.findById(data.getKaryawan()).orElse(null);
            if (ObjectUtils.isEmpty(karyawan)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, KARYAWAN));
                return responseService.generateErrorDataNotFound();
            }
            TrainingEntity training = trainingRepository.findById(data.getTraining()).orElse(null);
            if (ObjectUtils.isEmpty(training)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, TRAINING));
                return responseService.generateErrorDataNotFound();
            }
            karyawanTraining.setKaryawan(karyawan);
            karyawanTraining.setTraining(training);
            karyawanTraining = karyawanTrainingRepository.save(karyawanTraining);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_SAVE, karyawanTraining);

        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, SAVE, KARYAWAN_TRAINING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, SAVE, KARYAWAN_TRAINING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> update(KaryawanTrainingUpdateRequest data) {
        log.info(String.format(LOG_START, UPDATE, KARYAWAN_TRAINING));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_UPDATING, null);
        KaryawanTrainingEntity karyawanTraining = karyawanTrainingRepository.findById(data.getId()).orElse(null);
        try {
            if (karyawanTraining == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, KARYAWAN_TRAINING));
                return responseService.generateErrorDataNotFound();
            }
            KaryawanEntity karyawan = karyawanRepository.findById(data.getKaryawan()).orElse(null);
            if (ObjectUtils.isEmpty(karyawan)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, KARYAWAN));
                return responseService.generateErrorDataNotFound();
            }
            TrainingEntity training = trainingRepository.findById(data.getTraining()).orElse(null);
            if (ObjectUtils.isEmpty(training)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, TRAINING));
                return responseService.generateErrorDataNotFound();
            }
            karyawanTraining.setTanggal(DateUtil.stringToDate(data.getTanggal(), GeneralConstant.YYYY_MM_DD_HH_MM_SS));
            karyawanTraining.setKaryawan(karyawan);
            karyawanTraining.setTraining(training);
            karyawanTraining = karyawanTrainingRepository.save(karyawanTraining);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, karyawanTraining);

        } catch (
                Exception e) {
            log.error(String.format(LOG_ERROR, UPDATE, KARYAWAN_TRAINING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, UPDATE, KARYAWAN_TRAINING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> delete(Long id) {
        log.info(String.format(LOG_START, DELETE, KARYAWAN_TRAINING));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_DELETING, null);
        KaryawanTrainingEntity data = karyawanTrainingRepository.findById(id).orElse(null);
        try {
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, KARYAWAN_TRAINING));
                return responseService.generateErrorDataNotFound();
            }
            karyawanTrainingRepository.delete(data);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DELETE, null);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, DELETE, KARYAWAN_TRAINING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, DELETE, KARYAWAN_TRAINING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> findById(Long id) {
        log.info(String.format(LOG_START, GET_BY_ID, KARYAWAN_TRAINING));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        try {
            KaryawanTrainingEntity data = karyawanTrainingRepository.findById(id).orElse(null);
            if (ObjectUtils.isEmpty(data)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, KARYAWAN_TRAINING));
                return responseService.generateErrorDataNotFound();
            }
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);

        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_BY_ID, KARYAWAN_TRAINING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_BY_ID, KARYAWAN_TRAINING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> findAll(BasePagingRequest request) {
        log.info(String.format(LOG_START, GET_ALL, KARYAWAN_TRAINING));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        try {
            Page<KaryawanTrainingEntity> data = karyawanTrainingRepository.findAll(pageRequest);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_ALL, KARYAWAN_TRAINING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_ALL, KARYAWAN_TRAINING));
        }
        return response;
    }
}
