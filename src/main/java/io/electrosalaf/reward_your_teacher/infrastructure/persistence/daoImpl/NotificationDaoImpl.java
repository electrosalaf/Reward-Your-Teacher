package io.electrosalaf.reward_your_teacher.infrastructure.persistence.daoImpl;

import io.electrosalaf.reward_your_teacher.domain.dao.NotificationDao;
import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.message.NotificationEntity;
import io.electrosalaf.reward_your_teacher.infrastructure.persistence.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationDaoImpl extends CrudDaoImpl<NotificationEntity, Long> implements NotificationDao {
    private final NotificationRepository notificationRepository;

    protected NotificationDaoImpl(NotificationRepository notificationRepository) {
        super(notificationRepository);
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationEntity> findNotificationEntitiesByStudent(StudentEntity student) {
        return notificationRepository.findNotificationEntitiesByStudent(student);
    }

    @Override
    public List<NotificationEntity> findNotificationEntitiesByTeacher(TeacherEntity teacher) {
        return notificationRepository.findNotificationEntitiesByTeacher(teacher);
    }
}
