package com.trainingkaryawan.service.impl;

import com.trainingkaryawan.entity.DetailKaryawanEntity;
import com.trainingkaryawan.entity.KaryawanEntity;
import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.request.karyawan.KaryawanSaveRequest;
import com.trainingkaryawan.model.request.karyawan.KaryawanUpdateRequest;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.repository.DetailKaryawanRepository;
import com.trainingkaryawan.repository.KaryawanRepository;
import com.trainingkaryawan.service.CrudService;
import com.trainingkaryawan.service.ResponseService;
import com.trainingkaryawan.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.trainingkaryawan.constant.GeneralConstant.*;
import static com.trainingkaryawan.constant.GeneralConstant.SAVE;

@Slf4j
@Service
public class KaryawanServiceImpl implements CrudService<KaryawanSaveRequest, KaryawanUpdateRequest, Pair<HttpStatus, GeneralResponse<Object>>> {
    private final KaryawanRepository karyawanRepository;
    private final DetailKaryawanRepository detailKaryawanRepository;
    private final ResponseService responseService;

    public KaryawanServiceImpl(KaryawanRepository karyawanRepository, DetailKaryawanRepository detailKaryawanRepository, ResponseService responseService) {
        this.karyawanRepository = karyawanRepository;
        this.detailKaryawanRepository = detailKaryawanRepository;
        this.responseService = responseService;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> save(KaryawanSaveRequest data) {
        log.info(String.format(LOG_START, SAVE, KARYAWAN));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_SAVING, null);
        KaryawanEntity karyawan = new KaryawanEntity(data);
        DetailKaryawanEntity detailKaryawan = new DetailKaryawanEntity(data.getDetailKaryawan());
        try {
            detailKaryawan = detailKaryawanRepository.save(detailKaryawan);
            karyawan.setDetailKaryawan(detailKaryawan);
            karyawan = karyawanRepository.save(karyawan);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_SAVE, karyawan);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, SAVE, KARYAWAN, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, SAVE, KARYAWAN));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> update(KaryawanUpdateRequest data) {
        log.info(String.format(LOG_START, UPDATE, KARYAWAN));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_UPDATING, null);
        KaryawanEntity karyawan = karyawanRepository.findById(data.getId()).orElse(null);
        try {
            if (karyawan == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, KARYAWAN));
                response = responseService.generateErrorDataNotFound(EMPLOYEE_ENTITY_NAME);
            } else {
                karyawan.getDetailKaryawan().setNik(data.getDetailKaryawan().getNik());
                karyawan.getDetailKaryawan().setNpwp(data.getDetailKaryawan().getNpwp());

                detailKaryawanRepository.save(karyawan.getDetailKaryawan());
                karyawan.setNama(data.getNama());
                karyawan.setAlamat(data.getAlamat());
                karyawan.setStatus(data.getStatus());
                karyawan.setDob(DateUtil.stringToDate(data.getDob(), YYYY_MM_DD));
                karyawan = karyawanRepository.save(karyawan);
                response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, karyawan);
            }
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, UPDATE, KARYAWAN, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, UPDATE, KARYAWAN));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> delete(Long id) {
        log.info(String.format(LOG_START, DELETE, KARYAWAN));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_DELETING, null);
        KaryawanEntity data = karyawanRepository.findById(id).orElse(null);
        try {
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, KARYAWAN));
                response = responseService.generateErrorDataNotFound(EMPLOYEE_ENTITY_NAME);
            } else {
                karyawanRepository.delete(data);
                response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, null);
            }
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, DELETE, KARYAWAN, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, DELETE, KARYAWAN));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> findById(Long id) {
        log.info(String.format(LOG_START, GET_BY_ID, KARYAWAN));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        try {
            KaryawanEntity data = karyawanRepository.findById(id).orElse(null);
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, KARYAWAN));
                response = responseService.generateErrorDataNotFound(EMPLOYEE_ENTITY_NAME);
            }
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_BY_ID, KARYAWAN, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_BY_ID, KARYAWAN));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> findAll(BasePagingRequest request) {
        log.info(String.format(LOG_START, GET_ALL, KARYAWAN));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        try {
            Page<KaryawanEntity> data = karyawanRepository.findAll(pageRequest);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_ALL, KARYAWAN, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_ALL, KARYAWAN));
        }
        return response;
    }
}
