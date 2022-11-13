package io.electrosalaf.reward_your_teacher.infrastructure.persistence.daoImpl;

import io.electrosalaf.reward_your_teacher.domain.dao.AppUserDao;
import io.electrosalaf.reward_your_teacher.domain.entities.AppUserEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.enums.Role;
import io.electrosalaf.reward_your_teacher.infrastructure.persistence.repository.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AppDaoUserImpl extends CrudDaoImpl<AppUserEntity, Long> implements AppUserDao {
    private final AppUserRepository appUserRepository;

    protected AppDaoUserImpl(AppUserRepository appUserRepository) {
        super(appUserRepository);
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUserEntity findAppUserEntityByEmail(String email) {
        return appUserRepository.findAppUserEntityByEmail(email);
    }

    @Override
    public AppUserEntity findAppUserEntityByEmailAndRole(String email, Role role) {
        return appUserRepository.findAppUserEntityByEmailAndRole(email,role);
    }
}
