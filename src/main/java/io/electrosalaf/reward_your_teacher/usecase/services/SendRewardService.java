package io.electrosalaf.reward_your_teacher.usecase.services;

import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.SendRewardRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.SendRewardResponse;

import java.math.BigDecimal;

public interface SendRewardService {

    SendRewardResponse sendRewardResponse(SendRewardRequest sendRewardRequest);

    BigDecimal getStudentWalletBalance(StudentEntity studentEntity);

}
