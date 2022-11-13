package io.electrosalaf.reward_your_teacher.usecase.services;

import io.electrosalaf.reward_your_teacher.usecase.payload.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    List<TransactionResponse> getStudentTransactions(int offset, int pageSize);

    List<TransactionResponse> getTeacherTransactions(int offset, int pageSize);
}
