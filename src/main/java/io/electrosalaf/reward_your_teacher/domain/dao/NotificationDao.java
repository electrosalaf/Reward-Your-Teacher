package io.electrosalaf.reward_your_teacher.domain.dao;

import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.message.NotificationEntity;

import java.util.List;

public interface NotificationDao extends CrudDao<NotificationEntity, Long> {
  List<NotificationEntity> findNotificationEntitiesByStudent(StudentEntity student);

  List<NotificationEntity> findNotificationEntitiesByTeacher(TeacherEntity teacher);

}
