package io.electrosalaf.reward_your_teacher.usecase.services;

import io.electrosalaf.reward_your_teacher.usecase.payload.request.PayStackTransactionRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.PayStackTransactionResponse;

public interface PaymentService {
     PayStackTransactionResponse initTransaction(PayStackTransactionRequest request) throws Exception;
}

