package com.trainingkaryawan.service.impl;

import com.trainingkaryawan.entity.KaryawanEntity;
import com.trainingkaryawan.entity.RekeningEntity;
import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.request.rekening.RekeningSaveRequest;
import com.trainingkaryawan.model.request.rekening.RekeningUpdateRequest;
import com.trainingkaryawan.model.response.GeneraleResponse;
import com.trainingkaryawan.model.response.rekening.KaryawanModel;
import com.trainingkaryawan.model.response.rekening.RekeningResponse;
import com.trainingkaryawan.repository.KaryawanRepository;
import com.trainingkaryawan.repository.RekeningRepository;
import com.trainingkaryawan.service.CrudService;
import com.trainingkaryawan.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.trainingkaryawan.constant.GeneralConstant.*;

@Slf4j
@Service
public class RekeningServiceImpl implements CrudService<RekeningSaveRequest, RekeningUpdateRequest, Pair<HttpStatus, GeneraleResponse<Object>>> {
    private final KaryawanRepository karyawanRepository;
    private final RekeningRepository rekeningRepository;
    private final ResponseService responseService;

    public RekeningServiceImpl(KaryawanRepository karyawanRepository, RekeningRepository rekeningRepository, ResponseService responseService) {
        this.karyawanRepository = karyawanRepository;
        this.rekeningRepository = rekeningRepository;
        this.responseService = responseService;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> save(RekeningSaveRequest data) {
        log.info(String.format(LOG_START, SAVE, REKENING));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_SAVING, null);
        RekeningEntity rekening = new RekeningEntity(data);
        try {
            KaryawanEntity karyawan = karyawanRepository.findById(data.getKaryawan()).orElse(null);
            if(ObjectUtils.isEmpty(karyawan)){
                log.info(String.format(LOG_ERROR_NOT_FOUND, KARYAWAN));
                return responseService.generateErrorDataNotFound();
            }
            rekening.setKaryawan(karyawan);
            rekening = rekeningRepository.save(rekening);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_SAVE, new RekeningResponse(rekening, new KaryawanModel(rekening.getKaryawan())));
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, SAVE, REKENING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, SAVE, REKENING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> update(RekeningUpdateRequest data) {
        log.info(String.format(LOG_START, UPDATE, REKENING));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_UPDATING, null);
        RekeningEntity rekening = rekeningRepository.findById(data.getId()).orElse(null);
        try {
            if (rekening == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, REKENING));
                response = responseService.generateErrorDataNotFound();
            } else {
                KaryawanEntity karyawan = karyawanRepository.findById(data.getKaryawan()).orElse(null);
                if(ObjectUtils.isEmpty(karyawan)){
                    log.info(String.format(LOG_ERROR_NOT_FOUND, KARYAWAN));
                    response = responseService.generateErrorDataNotFound();
                }
                rekening.setRekening(data.getRekening());
                rekening.setJenis(data.getJenis());
                rekening.setNama(data.getNama());
                rekening.setKaryawan(karyawan);
                rekeningRepository.save(rekening);
                response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, new RekeningResponse(rekening, new KaryawanModel(rekening.getKaryawan())));
            }
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, UPDATE, REKENING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, UPDATE, REKENING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> delete(Long id) {
        log.info(String.format(LOG_START, DELETE, REKENING));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_DELETING, null);
        RekeningEntity data = rekeningRepository.findById(id).orElse(null);
        try {
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, REKENING));
                response = responseService.generateErrorDataNotFound();
            } else {
                rekeningRepository.delete(data);
                response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, null);
            }
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, DELETE, REKENING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, DELETE, REKENING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> findById(Long id) {
        log.info(String.format(LOG_START, GET_BY_ID, REKENING));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        try {
            RekeningEntity data = rekeningRepository.findById(id).orElse(null);
            if (ObjectUtils.isEmpty(data)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, REKENING));
                response = responseService.generateErrorDataNotFound();
            } else {
                response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, new RekeningResponse(data, new KaryawanModel(data.getKaryawan())));
            }
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_BY_ID, REKENING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_BY_ID, REKENING));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> findAll(BasePagingRequest request) {
        log.info(String.format(LOG_START, GET_ALL, REKENING));
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        try {
            Page<RekeningEntity> data = rekeningRepository.findAll(pageRequest);
            data = data.map(rekening-> new RekeningResponse(rekening, new KaryawanModel(rekening.getKaryawan()))); //map from entity to model response
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_ALL, REKENING, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_ALL, REKENING));
        }
        return response;
    }
}
