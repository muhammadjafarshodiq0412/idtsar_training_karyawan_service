package com.trainingkaryawan.service.impl;

import com.trainingkaryawan.entity.OtpEntity;
import com.trainingkaryawan.entity.oauth.RoleEntity;
import com.trainingkaryawan.entity.oauth.UserEntity;
import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.request.BasePagingRequest;
import com.trainingkaryawan.model.request.RegisterUserRequest;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.repository.OtpRepository;
import com.trainingkaryawan.repository.RoleRepository;
import com.trainingkaryawan.repository.UserRepository;
import com.trainingkaryawan.service.CrudService;
import com.trainingkaryawan.service.ResponseService;
import com.trainingkaryawan.service.UserService;
import com.trainingkaryawan.util.OTPGenerator;
import com.trainingkaryawan.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${template.email.user.activation}")
    private String templateEmailUserActivation;
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${server.domain}")
    private String serverAddress;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OtpRepository otpRepository;
    private final OtpServiceImpl otpService;
    private final OTPGenerator otpGenerator;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final ResponseService responseService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           @Lazy PasswordEncoder passwordEncoder, ResponseService responseService,
                           OtpRepository otpRepository, OTPGenerator otpGenerator, EmailServiceImpl emailService,
                           @Lazy OtpServiceImpl otpService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.responseService = responseService;
        this.otpRepository = otpRepository;
        this.otpGenerator = otpGenerator;
        this.emailService = emailService;
        this.otpService = otpService;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(new UserEntity());
    }

    public Pair<HttpStatus, GeneralResponse<Object>> register(RegisterUserRequest request) {
        log.info(String.format(LOG_START, REGISTER, USER));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_REGISTER_USER, null);
        UserEntity userEntity;
        try {
            //step 1 check user udah ada atau belum ? klo udah ada maka return bad request dengan message email ini sudah terdaftar
            userEntity = userRepository.findByEmail(request.getEmail()).orElse(null);
            if (ObjectUtils.isEmpty(userEntity)) { //jika user belum ada maka insert ke table use
                // step 2 jika email belum terdaftar, maka save save user dengan set property isActive = false
                userEntity = new UserEntity();
                userEntity.setPassword(request.getPassword());
                userEntity.setUsername(request.getUsername());
                userEntity.setEmail(request.getEmail());
                userEntity.setFullName(request.getFullName());
                userEntity.setDomicile(request.getDomicile());
                userEntity.setGender(request.getGender());
                userEntity.setPhoneNumber(request.getPhoneNumber());
                userEntity.setIsActive(false); //set isActive = false agar user tidak bisa langsung login, harus di aktivasi dulu
                //nah untuk mapping role user, perlu looping role dari request
                for (Long roleId : request.getRoles()) {
                    RoleEntity role = roleRepository.findById(roleId).orElse(null); // get data role entity sesuai roleId dari request
                    if (!ObjectUtils.isEmpty(role)) { //check apakah role tidak kosong ?
                        userEntity.addRole(role); // jika role ada maka mapping ke user entity dengan addRole
                    }
                }
                save(userEntity); //panggil method save, sampai sini user sudah berhasil di save ke database
            }else{
                if(userEntity.getIsActive()){ // dan jika user sudah aktif maka return data user sudah ada, artinya email itu sudah terdaftar dan aktif
                    log.info(String.format(LOG_ERROR_DATA_ALREADY_EXIST, USER));
                    return responseService.generateErrorDataExist(USER_ENTITY_NAME);
                }
            }
            // step 3 save ke table OTP, cara generete otp nya otpGenerator.generateOTP() bisa nyontek ke method requestOtp di class OtpServiceImpl
            OtpEntity otpEntity = otpRepository.findByEmail(request.getEmail()).orElse(null);
            if (ObjectUtils.isEmpty(otpEntity)) {
                OtpEntity otp = new OtpEntity();
                otp.setEmail(userEntity.getEmail());
                Pair<HttpStatus, GeneralResponse<Object>> saveOtp = otpService.save(otp);
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
            // step 4 kirim email aktivasi
            String linkActivationUser = serverAddress.concat(":").concat(serverPort).concat(contextPath).concat("/user/activation?email=").concat(otpEntity.getEmail()).concat("&otp=").concat(otpEntity.getOtp());
            emailService.sendEmail(otpEntity.getEmail(), "Request OTP forget password", templateEmailUserActivation.replace("{link-activation-user}", linkActivationUser));
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_REGISTER_USER, null);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, REGISTER, USER, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, REGISTER, USER));
        }
        return response;
    }

    public Pair<HttpStatus, GeneralResponse<Object>> activation(String email, String otp) {
        log.info(String.format(LOG_START, ACTIVATION_USER, ""));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_ACTIVATION_USER, null);
        try {
            OtpEntity otpEntity = otpRepository.findByEmailAndOtp(email, otp).orElse(null);
            if (ObjectUtils.isEmpty(otpEntity)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, OTP));
                return responseService.generateErrorDataNotFound(OTP_ENTITY_NAME);
            }else if(otpEntity.isVerified()){
                log.info("otp has been verified");
                return responseService.generateErrorResponse(ResponseType.FAILED_VALIDATE_OTP_VERIFIED, null);
            }

            boolean expired = TimeUtil.isExpired(otpEntity.getExpirationTime());
            if (expired) {
                return responseService.generateErrorResponse(ResponseType.FAILED_VALIDATE_OTP_EXPIRED, null);
            }

            UserEntity userEntity = userRepository.findByEmail(email).orElse(null);
            if (ObjectUtils.isEmpty(userEntity)) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, USER));
                return responseService.generateErrorDataNotFound(USER_ENTITY_NAME);
            }

            userEntity.setIsActive(true); //set user IsActive = true
            userRepository.save(userEntity); // update user
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_ACTIVATION_USER, null);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, ACTIVATION_USER, "", e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, ACTIVATION_USER, ""));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> save(UserEntity data) {
        log.info(String.format(LOG_START, SAVE, USER));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_SAVING, null);
        try {
            data.setPassword(passwordEncoder.encode(data.getPassword())); //passwordEncoder.encode ini untuk meng-enkripsi password
            UserEntity entity = userRepository.save(data);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_SAVE, entity);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, SAVE, USER, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, SAVE, USER));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> update(UserEntity data) {
        log.info(String.format(LOG_START, UPDATE, USER));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_UPDATING, null);
        UserEntity entity = userRepository.findById(data.getId()).orElse(null);
        try {
            if (entity == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, USER));
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
            log.error(String.format(LOG_ERROR, UPDATE, USER, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, UPDATE, USER));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> delete(Long id) {
        log.info(String.format(LOG_START, DELETE, USER));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_DELETING, null);
        UserEntity data = userRepository.findById(id).orElse(null);
        try {
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, USER));
                return responseService.generateErrorDataNotFound(USER_ENTITY_NAME);
            }
            data.getRoles().stream().map(roleEntity -> roleRepository.findById(roleEntity.getId())).filter(Optional::isPresent).map(Optional::get).forEach(data::removeRole);
            userRepository.delete(data);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_UPDATE, null);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, DELETE, USER, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, DELETE, USER));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> findById(Long id) {
        log.info(String.format(LOG_START, GET_BY_ID, USER));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        try {
            UserEntity data = userRepository.findById(id).orElse(null);
            if (data == null) {
                log.info(String.format(LOG_ERROR_NOT_FOUND, USER));
                return responseService.generateErrorDataNotFound(USER_ENTITY_NAME);
            }
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_BY_ID, USER, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_BY_ID, USER));
        }
        return response;
    }

    @Override
    public Pair<HttpStatus, GeneralResponse<Object>> findAll(BasePagingRequest request) {
        log.info(String.format(LOG_START, GET_ALL, USER));
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        try {
            Page<UserEntity> data = userRepository.findAll(pageRequest);
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_DATA_FOUND, data);
        } catch (Exception e) {
            log.error(String.format(LOG_ERROR, GET_ALL, USER, e.getMessage()), e);
        } finally {
            log.info(String.format(LOG_END, GET_ALL, USER));
        }
        return response;
    }

//    @Override
//    public Pair<HttpStatus, GeneraleResponse<Object>> findCurrentUser() {
//        log.info(String.format(LOG_START, "getCurrentUser", USER));
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
