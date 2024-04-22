package com.trainingkaryawan.service.impl;

import com.trainingkaryawan.entity.oauth.UserEntity;
import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.repository.RoleRepository;
import com.trainingkaryawan.repository.UserRepository;
import com.trainingkaryawan.service.CrudService;
import com.trainingkaryawan.service.ResponseService;
import com.trainingkaryawan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

import static com.trainingkaryawan.constant.GeneralConstant.*;


@Slf4j
@Service
public class UserServiceImpl implements UserService, CrudService<UserEntity, UserEntity, Pair<HttpStatus, GeneralResponse<Object>>>, UserDetailsService {
    private static final String entityName = "user";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResponseService responseService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder, ResponseService responseService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.responseService = responseService;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(new UserEntity());
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> save(UserEntity data) {
        log.info(String.format(LOG_START, SAVE, entityName));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_SAVING, null);
        try {
            data.setPassword(passwordEncoder.encode(data.getPassword()));
            UserEntity entity = userRepository.save(data);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_SAVE, entity);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, SAVE, entityName, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, SAVE, entityName));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> update(UserEntity data) {
        log.info(String.format(LOG_START, UPDATE, entityName));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_UPDATING, null);
        UserEntity entity = userRepository.findById(data.getId()).orElse(null);
        try {
            if (entity == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, entityName));
                return responseService.generateErrorDataNotFound(USER_ENTITY_NAME);
            }
            BeanUtils.copyProperties(data, entity, "roles", "password");
            if (!ObjectUtils.isEmpty(data.getPassword())) {
                entity.setPassword(passwordEncoder.encode(data.getPassword()));
            }
            entity.getRoles().clear();
            data.getRoles().forEach(entity::addRole);
            entity = userRepository.save(entity);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, entity);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, UPDATE, entityName, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, UPDATE, entityName));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> delete(Long id) {
        log.info(String.format(LOG_START, DELETE, entityName));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_DELETING, null);
        UserEntity data = userRepository.findById(id).orElse(null);
        try {
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, entityName));
                return responseService.generateErrorDataNotFound(USER_ENTITY_NAME);
            }
            data.getRoles().stream().map(roleEntity -> roleRepository.findById(roleEntity.getId())).filter(Optional::isPresent).map(Optional::get).forEach(data::removeRole);
            userRepository.delete(data);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, null);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, DELETE, entityName, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, DELETE, entityName));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> findById(Long id) {
        log.info(String.format(LOG_START, GET_BY_ID, entityName));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        try {
            UserEntity data = userRepository.findById(id).orElse(null);
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, entityName));
                return responseService.generateErrorDataNotFound(USER_ENTITY_NAME);
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
    public Pair<HttpStatus, GeneralResponse<Object>> findAll(BasePagingRequest request) {
        log.info(String.format(LOG_START, GET_ALL, entityName));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        try {
            Page<UserEntity> data = userRepository.findAll(pageRequest);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_ALL, entityName, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_ALL, entityName));
        }
        return response;
    }

//    @Override
//    public Pair<HttpStatus, GeneraleResponse<Object>> findCurrentUser() {
//        log.info(String.format(LOG_START, "getCurrentUser", entityName));
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            log.info(LOG_USER_NOT_AUTHENTICATED);
//            return null;
//        }
//
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof UserEntity) {
//            log.info(LOG_USER_AUTHENTICATED);
//            return (UserEntity) principal;
//        }
//        log.info(LOG_USER_AUTHENTICATED_NOT_MATCH);
//        return null;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
