package com.trainingkaryawan.service.impl;

import com.trainingkaryawan.entity.OtpEntity;
import com.trainingkaryawan.entity.oauth.UserEntity;
import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.request.otp.ChangePasswordRequest;
import com.trainingkaryawan.model.request.otp.SendOtpRequest;
import com.trainingkaryawan.model.request.otp.ValidateOtpRequest;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.repository.OtpRepository;
import com.trainingkaryawan.repository.UserRepository;
import com.trainingkaryawan.service.CrudService;
import com.trainingkaryawan.service.ResponseService;
import com.trainingkaryawan.util.OTPGenerator;
import com.trainingkaryawan.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.trainingkaryawan.constant.GeneralConstant.*;

@Slf4j
@Service
public class OtpServiceImpl implements CrudService<OtpEntity, OtpEntity, Pair<HttpStatus, GeneralResponse<Object>>> {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final ResponseService responseService;
    private final EmailServiceImpl mailService;
    private final OTPGenerator otpGenerator;
    private final PasswordEncoder passwordEncoder;

    @Value("${template.email.request.otp}")
    private String templateEmailRequestOtp;

    @Autowired
    public OtpServiceImpl(OtpRepository otpRepository, UserRepository userRepository, ResponseService responseService,
                          EmailServiceImpl mailService, OTPGenerator otpGenerator, PasswordEncoder passwordEncoder) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.responseService = responseService;
        this.mailService = mailService;
        this.otpGenerator = otpGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> save(OtpEntity data) {
        log.info(String.format(LOG_START, SAVE, OTP));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_SAVING, null);
        try {
            data.setOtp(otpGenerator.generateOTP());
            data.setVerified(false);
            data.setExpirationTime(TimeUtil.calculateExpirationTime(1));
            OtpEntity otpEntity = otpRepository.save(data);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_SAVE, otpEntity);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, SAVE, OTP, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, SAVE, OTP));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> update(OtpEntity data) {
        log.info(String.format(LOG_START, UPDATE, OTP));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_UPDATING, null);
        OtpEntity otpEntity = otpRepository.findById(data.getId()).orElse(null);
        try {
            if (ObjectUtils.isEmpty(otpEntity)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, OTP));
                return responseService.generateErrorDataNotFound(OTP_ENTITY_NAME);
            }
            otpEntity.setVerified(true);
            otpRepository.save(otpEntity);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, otpEntity);

        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, UPDATE, OTP, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, UPDATE, OTP));
        }
        return response;
    }

    public Pair<HttpStatus, GeneralResponse<Object>> requestOtp(SendOtpRequest request) {
        log.info(String.format(LOG_START, VALIDATE_OTP, ""));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_SEND_OTP, null);
        try {

            UserEntity userEntity = userRepository.findByEmail(request.getEmail()).orElse(null);
            if (ObjectUtils.isEmpty(userEntity)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, USER));
                return responseService.generateErrorDataNotFound(USER_ENTITY_NAME);
            }
            OtpEntity otpEntity = otpRepository.findByEmail(request.getEmail()).orElse(null);
            if (ObjectUtils.isEmpty(otpEntity)) {
                OtpEntity otp = new OtpEntity();
                otp.setEmail(userEntity.getEmail());

                Pair<HttpStatus, GeneralResponse<Object>> saveOtp = save(otp);
                if (saveOtp.getFirst() != HttpStatus.OK) {
                    response = responseService.generateErrorResponse(ResponseType.FAILED_SEND_OTP, null);
                }
                otpEntity = (OtpEntity) saveOtp.getSecond().getData();
            } else {
                otpEntity.setOtp(otpGenerator.generateOTP());
                otpEntity.setVerified(false);
                otpEntity.setExpirationTime(TimeUtil.calculateExpirationTime(5));
                otpEntity = otpRepository.save(otpEntity);
            }
            mailService.sendEmail(otpEntity.getEmail(), "Request OTP forget password", templateEmailRequestOtp.replace("{fullname}", userEntity.getFullName()).replace("{otp}", otpEntity.getOtp()));
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_SEND_OTP, null);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, VALIDATE_OTP, "", e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, VALIDATE_OTP, ""));
        }
        return response;
    }

    public Pair<HttpStatus, GeneralResponse<Object>> validateOtp(ValidateOtpRequest request) {
        log.info(String.format(LOG_START, VALIDATE_OTP, ""));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_VALIDATE_OTP, null);
        try {
            OtpEntity otpEntity = otpRepository.findByEmailAndOtp(request.getEmail(), request.getOtp()).orElse(null);
            if (ObjectUtils.isEmpty(otpEntity)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, OTP));
                return responseService.generateErrorDataNotFound(OTP_ENTITY_NAME);
            }
            boolean expired = TimeUtil.isExpired(otpEntity.getExpirationTime());
            if (expired) {
                return responseService.generateErrorResponse(ResponseType.FAILED_VALIDATE_OTP_EXPIRED, null);
            }
            otpEntity.setVerified(true);
            otpRepository.save(otpEntity);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_VALIDATE_OTP, null);

        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, VALIDATE_OTP, "", e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, VALIDATE_OTP, ""));
        }
        return response;
    }

    public Pair<HttpStatus, GeneralResponse<Object>> changePassword(ChangePasswordRequest request) {
        log.info(String.format(LOG_START, CHANGE_PASSWORD, ""));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_VALIDATE_OTP, null);
        try {
            OtpEntity otpEntity = otpRepository.findByEmailAndOtp(request.getEmail(), request.getOtp()).orElse(null);
            if (ObjectUtils.isEmpty(otpEntity)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, OTP));
                return responseService.generateErrorDataNotFound(OTP_ENTITY_NAME);
            }else if(!otpEntity.isVerified()){
                log.info("otp not verified");
                return responseService.generateErrorResponse(ResponseType.FAILED_VALIDATE_OTP_NOT_VERIFIED, null);
            }else if(!request.getNewPassword().equals(request.getConfirmNewPassword())){
                log.info("new password and confirm new password not match");
                return responseService.generateErrorResponse(ResponseType.FAILED_PASSWORD_NOT_MATCH, null);
            }

            UserEntity userEntity = userRepository.findByEmail(request.getEmail()).orElse(null);
            if (ObjectUtils.isEmpty(userEntity)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, USER));
                return responseService.generateErrorDataNotFound(USER_ENTITY_NAME);
            }

            userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(userEntity);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_CHANGE_PASSWORD, null);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, CHANGE_PASSWORD, "", e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, CHANGE_PASSWORD, ""));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> delete(Long id) {
        return null;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> findById(Long id) {
        return null;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> findAll(BasePagingRequest request) {
        return null;
    }
}
