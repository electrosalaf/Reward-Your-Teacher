package io.electrosalaf.reward_your_teacher.usecase.services;

import io.electrosalaf.reward_your_teacher.domain.entities.email.ConfirmationTokenEntity;

public interface ConfirmationTokenService {
     void saveConfirmationToken(ConfirmationTokenEntity token);
     ConfirmationTokenEntity getToken(String token);
     int setConfirmedAt(String token);
}
