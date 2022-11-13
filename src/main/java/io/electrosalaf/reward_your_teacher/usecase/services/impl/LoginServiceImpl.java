package io.electrosalaf.reward_your_teacher.usecase.services.impl;

import io.electrosalaf.reward_your_teacher.domain.dao.AppUserDao;
import io.electrosalaf.reward_your_teacher.domain.dao.StudentDao;
import io.electrosalaf.reward_your_teacher.domain.dao.TeacherDao;
import io.electrosalaf.reward_your_teacher.domain.dao.WalletDao;
import io.electrosalaf.reward_your_teacher.domain.entities.AppUserEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.enums.Role;
import io.electrosalaf.reward_your_teacher.domain.entities.transact.WalletEntity;
import io.electrosalaf.reward_your_teacher.infrastructure.configuration.security.JwtService;
import io.electrosalaf.reward_your_teacher.infrastructure.error_handler.AuthenticationFailedException;
import io.electrosalaf.reward_your_teacher.infrastructure.error_handler.CustomNotFoundException;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.LoginRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.SocialLoginRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.LoginResponse;
import io.electrosalaf.reward_your_teacher.usecase.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@AllArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService {
    private final StudentDao studentDao;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TeacherDao teacherDao;
    private final AppUserDao appUserDao;
    private final PasswordEncoder passwordEncoder;
    private final WalletDao walletDao;

    @Override
    public LoginResponse loginStudent(LoginRequest studentLoginRequest)  {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(studentLoginRequest
                        .getEmail(), studentLoginRequest.getPassword()));
        if (!auth.isAuthenticated() ) {
            throw new AuthenticationFailedException("Wrong email or password");
        }
        AppUserEntity appUserEntity = appUserDao
                .findAppUserEntityByEmailAndRole(studentLoginRequest.getEmail(), Role.STUDENT);
        if(appUserEntity == null){
           throw new CustomNotFoundException("User does not exist");
        }
        if(!appUserEntity.getRole().equals(Role.STUDENT)){
            throw new AuthenticationFailedException("Unauthorised");
        }
        if(!appUserEntity.isVerified()){
            throw new AuthenticationFailedException("User not verified");
        }
        String token = "Bearer " + jwtService
                .generateToken(new org.springframework.security.core.userdetails
                        .User(studentLoginRequest.getEmail(), studentLoginRequest.getPassword(), new ArrayList<>()));
        return new LoginResponse(token);
    }

    @Override
    public LoginResponse studentSocialLogin(SocialLoginRequest socialLoginRequest) {
        socialLoginRequest.setPassword("");
        AppUserEntity appUser = appUserDao.findAppUserEntityByEmailAndRole(socialLoginRequest.getEmail(),Role.STUDENT);
        if (appUser == null) {
            AppUserEntity appUserEntity = AppUserEntity.builder()
                    .password(passwordEncoder.encode(socialLoginRequest.getPassword()))
                    .email(socialLoginRequest.getEmail())
                    .isVerified(true)
                    .role(Role.STUDENT)
                    .build();
            AppUserEntity user = appUserDao.saveRecord(appUserEntity);

            String name = socialLoginRequest.getFirstName() + " " + socialLoginRequest.getLastName();
            StudentEntity studentEntity = StudentEntity.builder()
                            .name(name)
                            .displayPicture(socialLoginRequest.getDisplayPicture())
                            .appUserEntity(user).build();

            studentDao.saveRecord(studentEntity);
            WalletEntity wallet = new WalletEntity();
            wallet.setBalance(new BigDecimal("0.00"));
            wallet.setStudent(studentEntity);
            wallet.setTotalMoneySent(new BigDecimal("0.00"));
            walletDao.saveRecord(wallet);

        }
        String token = "Bearer " + jwtService.generateToken(new org.springframework.security.core
                .userdetails.User(socialLoginRequest.getEmail(), socialLoginRequest
                .getFirstName(), new ArrayList<>()));
        return new LoginResponse(token);
    }

    @Override
    public LoginResponse loginTeacher(LoginRequest teacherLoginRequest)  {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(teacherLoginRequest
                        .getEmail(), teacherLoginRequest.getPassword()));
        if (!auth.isAuthenticated()) {
            throw new AuthenticationFailedException(("wrong email or password"));
        }
        AppUserEntity appUserEntity = appUserDao
                .findAppUserEntityByEmailAndRole(teacherLoginRequest.getEmail(),Role.TEACHER);
        if(appUserEntity == null){
           throw new CustomNotFoundException("User does not exist");
        }
        if(!appUserEntity.getRole().equals(Role.TEACHER)){
            throw new AuthenticationFailedException("Unauthorised");
        }
        if(!appUserEntity.isVerified()){
            throw new AuthenticationFailedException("User not verified");
        }
        String token = "Bearer " + jwtService
                .generateToken(new org.springframework.security.core.userdetails
                        .User(teacherLoginRequest.getEmail(), teacherLoginRequest.getPassword(), new ArrayList<>()));
        return new LoginResponse(token);

    }

    @Override
    public LoginResponse teacherSocialLogin(SocialLoginRequest socialLoginRequest) {
        socialLoginRequest.setPassword("");

        AppUserEntity appUser = appUserDao.findAppUserEntityByEmailAndRole(socialLoginRequest.getEmail(),Role.TEACHER);
        if (appUser == null) {
            AppUserEntity appUserEntity = AppUserEntity.builder()
                    .password(passwordEncoder.encode(socialLoginRequest.getPassword()))
                    .email(socialLoginRequest.getEmail())
                    .role(Role.TEACHER)
                    .isVerified(true)
                    .build();
            AppUserEntity user = appUserDao.saveRecord(appUserEntity);

            String name = socialLoginRequest.getFirstName() + " " + socialLoginRequest.getLastName();
            TeacherEntity teacherEntity = TeacherEntity.builder()
                            .name(name)
                            .appUserEntity(user)
                            .displayPicture(socialLoginRequest.getDisplayPicture()).build();
            teacherDao.saveRecord(teacherEntity);
            WalletEntity wallet = new WalletEntity();
            wallet.setBalance(new BigDecimal("0.00"));
            wallet.setTeacher(teacherEntity);
            wallet.setTotalMoneySent(new BigDecimal("0.00"));
            walletDao.saveRecord(wallet);
        }
        String token = "Bearer " + jwtService.generateToken(new org.springframework.security.core
                .userdetails.User(socialLoginRequest.getEmail(), socialLoginRequest
                .getFirstName(), new ArrayList<>()));
        return new LoginResponse(token);
    }
}
