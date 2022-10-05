package io.electrosalaf.reward_your_teacher.domain.dao;

import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.transact.WalletEntity;

import java.util.Optional;

public interface WalletDao extends CrudDao<WalletEntity, Long> {
    WalletEntity findWalletEntityByStudent(StudentEntity student);
    Optional<WalletEntity> findWalletEntityByTeacher(TeacherEntity teacher);
}
