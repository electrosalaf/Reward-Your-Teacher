package io.electrosalaf.reward_your_teacher.infrastructure.persistence.repository;

import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.transact.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Page<TransactionEntity> findTransactionEntitiesByStudent(Pageable pageable, StudentEntity student);
    Page<TransactionEntity> findTransactionEntitiesByTeacher(Pageable pageable, TeacherEntity teacher);
}
