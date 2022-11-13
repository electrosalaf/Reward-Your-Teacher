package io.electrosalaf.reward_your_teacher.usecase.services.impl;

import io.electrosalaf.reward_your_teacher.domain.dao.AppUserDao;
import io.electrosalaf.reward_your_teacher.domain.dao.StudentDao;
import io.electrosalaf.reward_your_teacher.domain.dao.TeacherDao;
import io.electrosalaf.reward_your_teacher.domain.dao.TransactionDao;
import io.electrosalaf.reward_your_teacher.domain.entities.AppUserEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.enums.Role;
import io.electrosalaf.reward_your_teacher.domain.entities.transact.TransactionEntity;
import io.electrosalaf.reward_your_teacher.infrastructure.configuration.security.UserDetails;
import io.electrosalaf.reward_your_teacher.infrastructure.error_handler.CustomNotFoundException;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.TransactionResponse;
import io.electrosalaf.reward_your_teacher.usecase.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;
    private final StudentDao studentDao;

    private final TeacherDao teacherDao;
    private final AppUserDao userDao;


    @Override
    public List<TransactionResponse> getStudentTransactions(int offset,int pageSize) {
        String email = UserDetails.getLoggedInUserDetails();

        AppUserEntity appUserEntity = userDao.findAppUserEntityByEmailAndRole(email, Role.STUDENT);
        if(appUserEntity == null){
           throw new CustomNotFoundException("User not found");
        }

        StudentEntity student = studentDao.getStudentEntityByAppUserEntity(appUserEntity);

        Pageable pageable = PageRequest.of(offset,pageSize);
        Page<TransactionEntity> pageList = transactionDao.findTransactionEntitiesByStudent(pageable,student);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        pageList.forEach(page->{
            TransactionResponse transactionResponse1 = TransactionResponse.builder()
                    .transactionType(page.getTransactionType())
                    .amount(page.getAmount())
                    .description(page.getDescription())
                    .createdAt(page.getCreatedAt())
                    .build();
            transactionResponses.add(transactionResponse1);

        });
        return transactionResponses;
    }


    @Override
    public List<TransactionResponse> getTeacherTransactions(int offset, int pageSize) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof org.springframework.security.core.userdetails.UserDetails)) {
            throw new CustomNotFoundException("user not found");
        }
        String email = ((org.springframework.security.core.userdetails.UserDetails)principal).getUsername();
        AppUserEntity appUserEntity = userDao.findAppUserEntityByEmailAndRole(email,Role.TEACHER);
        if(appUserEntity == null){
          throw new CustomNotFoundException("User not found");
        }

        TeacherEntity teacher = teacherDao.getTeacherEntityByAppUserEntity(appUserEntity);
        if(teacher == null){
            throw new CustomNotFoundException("Invalid user");
        }

        Pageable pageable = PageRequest.of(offset,pageSize);
        Page<TransactionEntity> pageList = transactionDao.findTransactionEntitiesByTeacher(pageable,teacher);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        pageList.forEach(page->{
            TransactionResponse transactionResponse1 = TransactionResponse.builder()
                    .transactionType(page.getTransactionType())
                    .amount(page.getAmount())
                    .description(page.getDescription())
                    .createdAt(page.getCreatedAt())
                    .build();
            transactionResponses.add(transactionResponse1);

        });
        return transactionResponses;
    }
}



