package io.electrosalaf.reward_your_teacher.usecase.services.impl;

import io.electrosalaf.reward_your_teacher.domain.dao.AppUserDao;
import io.electrosalaf.reward_your_teacher.domain.dao.StudentDao;
import io.electrosalaf.reward_your_teacher.domain.dao.TeacherDao;
import io.electrosalaf.reward_your_teacher.domain.dao.WalletDao;
import io.electrosalaf.reward_your_teacher.domain.entities.AppUserEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.transact.WalletEntity;
import io.electrosalaf.reward_your_teacher.infrastructure.error_handler.CustomNotFoundException;
import io.electrosalaf.reward_your_teacher.infrastructure.error_handler.InsufficientBalanceException;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.SendRewardRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.SendRewardResponse;
import io.electrosalaf.reward_your_teacher.usecase.services.SendRewardService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor
public class SendRewardServiceImpl implements SendRewardService {

    private final StudentDao studentDao;

    private final TeacherDao teacherDao;

    private final WalletDao walletDao;

    private final AppUserDao appUserDao;



    @Override
    public SendRewardResponse sendRewardResponse(SendRewardRequest sendRewardRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserDetails)) {
            throw new CustomNotFoundException("user not found");
        }

        String email = ((UserDetails) principal).getUsername();


        AppUserEntity appUserEntity = appUserDao.findAppUserEntityByEmail(email);

        if (appUserEntity == null){
            throw new CustomNotFoundException("user not found");
        }

        StudentEntity student = studentDao.getStudentEntityByAppUserEntity(appUserEntity);

        TeacherEntity teacher = teacherDao.getTeacherByNameAndPhoneNumber(sendRewardRequest.getTeacherName(), sendRewardRequest.getTeacherPhone());

        if (teacher != null) {
            if (getStudentWalletBalance(student).compareTo(sendRewardRequest.getAmount()) >= 0) {

                if(sendRewardRequest.getAmount().compareTo(new BigDecimal("0.00")) <= 0){
                    throw new IllegalArgumentException("invalid amount");
                }

                student.getWallet().setBalance(student.getWallet().getBalance().subtract(sendRewardRequest.getAmount()));
                teacher.getWallet().setBalance(teacher.getWallet().getBalance().add(sendRewardRequest.getAmount()));
                studentDao.saveRecord(student);
                teacherDao.saveRecord(teacher);

            } else {
                throw new InsufficientBalanceException("Unable to perform transaction because of an insufficient balance");
            }

        } else {
            throw new CustomNotFoundException("teacher not found");
        }


        return new SendRewardResponse(true,
                "money sent successfully to " + teacher.getName()
                        + "you now have " + student.getWallet().getBalance() + "in your wallet", LocalDateTime.now()
        );
    }

    @Override
    public BigDecimal getStudentWalletBalance(StudentEntity student) {


        WalletEntity wallet = walletDao.findWalletEntityByStudent(student);
        if (wallet == null){
            throw new CustomNotFoundException("user wallet does not exist");
        }
        return wallet.getBalance();
    }

}
