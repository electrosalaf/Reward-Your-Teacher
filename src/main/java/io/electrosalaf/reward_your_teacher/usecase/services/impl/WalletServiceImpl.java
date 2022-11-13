package io.electrosalaf.reward_your_teacher.usecase.services.impl;

import io.electrosalaf.reward_your_teacher.domain.dao.*;
import io.electrosalaf.reward_your_teacher.domain.entities.AppUserEntity;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.EmailDetailsRequest;
import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.enums.Role;
import io.electrosalaf.reward_your_teacher.domain.entities.enums.TransactionType;
import io.electrosalaf.reward_your_teacher.domain.entities.transact.TransactionEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.transact.WalletEntity;
import io.electrosalaf.reward_your_teacher.infrastructure.configuration.security.UserDetails;
import io.electrosalaf.reward_your_teacher.infrastructure.error_handler.CustomNotFoundException;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.FundWalletRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.PayStackTransactionRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.PayStackTransactionResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.PaymentResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.WalletResponse;
import io.electrosalaf.reward_your_teacher.usecase.services.PaymentService;
import io.electrosalaf.reward_your_teacher.usecase.services.WalletService;
import io.electrosalaf.reward_your_teacher.utils.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final EmailService emailService;
    private final StudentDao studentDao;

    private final TeacherDao teacherDao;
    private final WalletDao walletDao;
    private final PaymentService paymentService;
    private final TransactionDao transactionDao;
    private final AppUserDao appUserDao;

    @Override
    public WalletResponse getStudentWalletBalance() {
       String email = UserDetails.getLoggedInUserDetails();
        AppUserEntity appUserEntity = appUserDao.findAppUserEntityByEmailAndRole(email, Role.STUDENT);
        if(appUserEntity == null){
            throw new CustomNotFoundException("User not found");
        }

       StudentEntity student = studentDao.getStudentEntityByAppUserEntity(appUserEntity);

        if(student == null){
            throw new CustomNotFoundException("Invalid user");
        }
       WalletEntity wallet = walletDao.findWalletEntityByStudent(student);
        if(wallet == null){
            throw new CustomNotFoundException("Wallet not found or phone number missing");
        }


     return new WalletResponse(wallet.getBalance());

    }

    @Override
    public WalletResponse getTeacherWalletBalance() {
        String email = UserDetails.getLoggedInUserDetails();
        AppUserEntity appUserEntity = appUserDao.findAppUserEntityByEmailAndRole(email,Role.TEACHER);
               if(appUserEntity == null){
                  throw new CustomNotFoundException("User not found");
               }
        TeacherEntity teacher = teacherDao.getTeacherEntityByAppUserEntity(appUserEntity);
        if(teacher == null){
            throw new CustomNotFoundException("Invalid user");
        }


        WalletEntity wallet = walletDao.findWalletEntityByTeacher(teacher)
                .orElseThrow(()-> new CustomNotFoundException("Wallet not found or Phone number missing"));

        return new WalletResponse(wallet.getBalance());
    }
    @Override
    public PaymentResponse fundWallet (FundWalletRequest fundWalletRequest) throws Exception {
        String email = UserDetails.getLoggedInUserDetails();
        PayStackTransactionRequest payStackTransactionRequest = PayStackTransactionRequest
                .builder()
                .email(email)
                .amount(new BigDecimal(fundWalletRequest.getAmount()))
                .build();

        PayStackTransactionResponse transactionResponse = paymentService.initTransaction(payStackTransactionRequest);

        if (!transactionResponse.isStatus()) {
            throw new Exception("Payment not authorized");
        }
        AppUserEntity appUserEntity = appUserDao.findAppUserEntityByEmailAndRole(email,Role.STUDENT);
        if(appUserEntity == null){
            throw new CustomNotFoundException("Entity not found");
        }
        StudentEntity student = studentDao.getStudentEntityByAppUserEntity(appUserEntity);
        WalletEntity wallet = walletDao.findWalletEntityByStudent(student);
        if (wallet == null) {
            WalletEntity walletDao1 = WalletEntity.builder()
                    .balance(new BigDecimal(fundWalletRequest.getAmount()))
                    .student(student)
                    .totalMoneySent(new BigDecimal(fundWalletRequest.getAmount())).build();
            walletDao.saveRecord(walletDao1);
            TransactionEntity transaction = TransactionEntity.builder()
                    .transactionType(TransactionType.CREDIT.name())
                    .student(student)
                    .amount(new BigDecimal(fundWalletRequest.getAmount()))
                    .description(transactionResponse.getMessage())
                    .build();
            transactionDao.saveRecord(transaction);
            return new PaymentResponse("Success");

        }
        BigDecimal result = wallet.getBalance().add(new BigDecimal(fundWalletRequest.getAmount()));
        wallet.setBalance(result);
        wallet.setStudent(student);
        wallet.setTotalMoneySent(new BigDecimal(fundWalletRequest.getAmount()));
        walletDao.saveRecord(wallet);

        TransactionEntity transaction = TransactionEntity
                .builder()
                .transactionType(TransactionType.DEBIT.name())
                .student(student)
                .amount(new BigDecimal(fundWalletRequest.getAmount()))
                .description(transactionResponse.getMessage())
                .build();
        transactionDao.saveRecord(transaction);
        EmailDetailsRequest emailDetailsRequest = EmailDetailsRequest.builder()
                .msgBody(emailService.WalletFundingEmail(student.getName(),fundWalletRequest.getAmount()))
                .subject("PodA email")
                .recipient(appUserEntity.getEmail())
                .build();
        emailService.sendMailWithAttachment(emailDetailsRequest);

        return new PaymentResponse(transactionResponse.getData().getReference());
    }
}
