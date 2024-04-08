package com.trainingkaryawan.config;

import com.trainingkaryawan.entity.oauth.RoleEntity;
import com.trainingkaryawan.entity.oauth.UserEntity;
import com.trainingkaryawan.repository.RoleRepository;
import com.trainingkaryawan.repository.UserRepository;
import com.trainingkaryawan.service.impl.RoleServiceImpl;
import com.trainingkaryawan.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DbInit implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (userRepository.findAll().size() == 0 || roleRepository.findAll().size() == 0) {
            populateUserAndRole();
        }
    }

    protected void populateUserAndRole() {
        log.info("populate UserAndRole started");
        //after tha owner inserted, the insert the inverse
        RoleEntity roleSuperUser = roleRepository.save(new RoleEntity("SUPER_USER", "Role Super User"));
        RoleEntity roleAdmin = roleRepository.save(new RoleEntity("ADMIN", "Role Admin"));
        RoleEntity roleSecretary = roleRepository.save(new RoleEntity("SECRETARY", "Role Secretary"));
        RoleEntity roleEmployee = roleRepository.save(new RoleEntity("EMPLOYEE", "Role Employee"));

        UserEntity user1 = new UserEntity("superadmin", "superadmin@gmail.com", "Gita");
        user1.setPassword("123");
        user1.setRoles(List.of(roleSuperUser));
        UserEntity user2 = new UserEntity("admin", "admin@gmail.com", "Vina");
        user2.setPassword("123");
        user2.setRoles(List.of(roleAdmin));
        UserEntity user3 = new UserEntity("secretary", "secretary@gmail.com", "Via");
        user3.setPassword("123");
        user3.setRoles(List.of(roleSecretary));
        UserEntity user4 = new UserEntity("employee", "employee@gmail.com", "Heru");
        user4.setPassword("123");
        user4.setRoles(List.of(roleEmployee));

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        userService.save(user4);

    }

}
