package io.electrosalaf.reward_your_teacher.usecase.services;

import io.electrosalaf.reward_your_teacher.usecase.payload.request.FundWalletRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.PaymentResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.WalletResponse;

public interface WalletService {
    WalletResponse getStudentWalletBalance ();
    PaymentResponse fundWallet(FundWalletRequest fundWalletRequest) throws Exception;
    WalletResponse getTeacherWalletBalance();

}
