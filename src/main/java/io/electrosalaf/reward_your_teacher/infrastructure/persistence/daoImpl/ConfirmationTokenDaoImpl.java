package io.electrosalaf.reward_your_teacher.infrastructure.persistence.daoImpl;

import io.electrosalaf.reward_your_teacher.domain.dao.ConfirmationTokenDao;
import io.electrosalaf.reward_your_teacher.domain.entities.email.ConfirmationTokenEntity;
import io.electrosalaf.reward_your_teacher.infrastructure.persistence.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenDaoImpl extends CrudDaoImpl<ConfirmationTokenEntity, Long> implements ConfirmationTokenDao {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    protected ConfirmationTokenDaoImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        super(confirmationTokenRepository);
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public int updateConfirmedAt(String token, LocalDateTime now) {
        return confirmationTokenRepository.updateConfirmedAt(token,now);
    }

    @Override
    public ConfirmationTokenEntity findByToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }
}
