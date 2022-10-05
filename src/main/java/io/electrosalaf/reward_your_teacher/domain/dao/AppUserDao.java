package io.electrosalaf.reward_your_teacher.domain.dao;

import io.electrosalaf.reward_your_teacher.domain.entities.AppUserEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.enums.Role;

public interface AppUserDao extends CrudDao<AppUserEntity, Long>{
    AppUserEntity findAppUserEntityByEmail(String email);
    AppUserEntity findAppUserEntityByEmailAndRole(String email, Role role);
}
