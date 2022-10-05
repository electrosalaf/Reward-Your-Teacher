package io.electrosalaf.reward_your_teacher.domain.dao;

import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.transact.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionDao extends CrudDao<TransactionEntity, Long> {
    Page<TransactionEntity> findTransactionEntitiesByStudent(Pageable pageable, StudentEntity student);
    Page<TransactionEntity> findTransactionEntitiesByTeacher(Pageable pageable, TeacherEntity teacher);
}
