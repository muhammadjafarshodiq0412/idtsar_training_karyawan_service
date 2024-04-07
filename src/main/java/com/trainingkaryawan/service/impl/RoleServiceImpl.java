package com.trainingkaryawan.service.impl;

import static com.trainingkaryawan.constant.GeneralConstant.*;

import com.trainingkaryawan.entity.oauth.RoleEntity;
import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.response.GeneraleResponse;
import com.trainingkaryawan.repository.RoleRepository;
import com.trainingkaryawan.service.CrudService;
import com.trainingkaryawan.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class RoleServiceImpl implements CrudService<RoleEntity, RoleEntity, Pair<HttpStatus, GeneraleResponse<Object>>> {
    private static final String ENTITY_NAME = "Role";
    private final RoleRepository roleRepository;
    private final ResponseService responseService;

    public RoleServiceImpl(RoleRepository roleRepository, ResponseService responseService) {
        this.roleRepository = roleRepository;
        this.responseService = responseService;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> save(RoleEntity request) {
        log.info(String.format(LOG_START, SAVE, ENTITY_NAME));
        Pair<HttpStatus, GeneraleResponse<Object>> response;
        RoleEntity data = null;
        try {
            request.setName(request.getName());
            data = roleRepository.save(request);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_SAVE, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, SAVE, ENTITY_NAME, e.getMessage()), e);
            response = responseService.generateErrorResponse(ResponseType.FAILED_SAVE, data);
        } finally {
            log.info(String.format(LOG_END, SAVE, ENTITY_NAME));
        }

        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> update(RoleEntity request) {
        log.info(String.format(LOG_START, UPDATE, ENTITY_NAME));
        Pair<HttpStatus, GeneraleResponse<Object>> response;
        RoleEntity data = roleRepository.findById(request.getId()).orElse(null);
        try {
            if (ObjectUtils.isEmpty(data)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, ENTITY_NAME));
                return responseService.generateErrorDataNotFound();
            }
            data = roleRepository.save(request);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, UPDATE, ENTITY_NAME, e.getMessage()), e);
            response = responseService.generateErrorResponse(ResponseType.FAILED_UPDATE, data);
        } finally {
            log.info(String.format(LOG_END, UPDATE, ENTITY_NAME));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> delete(Long id) {
        log.info(String.format(LOG_START, DELETE, ENTITY_NAME));
        Pair<HttpStatus, GeneraleResponse<Object>> response;
        RoleEntity data = roleRepository.findById(id).orElse(null);
        try {
            if (ObjectUtils.isEmpty(data)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, ENTITY_NAME));
                return responseService.generateErrorDataNotFound();
            }
            roleRepository.delete(data);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DELETE, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, DELETE, ENTITY_NAME, e.getMessage()), e);
            response = responseService.generateErrorResponse(ResponseType.FAILED_DELETE, data);
        } finally {
            log.info(String.format(LOG_END, DELETE, ENTITY_NAME));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> findById(Long id) {
        log.info(String.format(LOG_START, GET_BY_ID, ENTITY_NAME));
        Pair<HttpStatus, GeneraleResponse<Object>> response;
        try {
            RoleEntity data = roleRepository.findById(id).orElse(null);
            if (ObjectUtils.isEmpty(data)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, ENTITY_NAME));
                return responseService.generateErrorDataNotFound();
            }
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_BY_ID, ENTITY_NAME, e.getMessage()), e);
            response = responseService.generateErrorResponse(ResponseType.INTERNAL_SERVER_ERROR, null);
        } finally {
            log.info(String.format(LOG_END, GET_BY_ID, ENTITY_NAME));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> findAll(BasePagingRequest request) {
        log.info(String.format(LOG_START, GET_ALL, ENTITY_NAME));
        Pair<HttpStatus, GeneraleResponse<Object>> response;
        try {
            PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
            Page<RoleEntity> data = roleRepository.findAll(pageRequest);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_ALL, ENTITY_NAME, e.getMessage()), e);
            response = responseService.generateErrorResponse(ResponseType.INTERNAL_SERVER_ERROR, null);
        } finally {
            log.info(String.format(LOG_END, GET_ALL, ENTITY_NAME));
        }
        return response;
    }
}
