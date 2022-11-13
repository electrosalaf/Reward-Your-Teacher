package io.electrosalaf.reward_your_teacher.infrastructure.persistence.repository;

import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.message.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findNotificationEntitiesByStudent(StudentEntity student);

    List<NotificationEntity> findNotificationEntitiesByTeacher(TeacherEntity teacher);
}
