package io.electrosalaf.reward_your_teacher.domain.dao;

import io.electrosalaf.reward_your_teacher.domain.entities.AppUserEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;

public interface StudentDao extends CrudDao<StudentEntity, Long> {
    StudentEntity getStudentEntityByAppUserEntity(AppUserEntity appUserEntity);

}
