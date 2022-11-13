package io.electrosalaf.reward_your_teacher.infrastructure.persistence.daoImpl;

import io.electrosalaf.reward_your_teacher.domain.dao.StudentDao;
import io.electrosalaf.reward_your_teacher.domain.entities.AppUserEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.infrastructure.persistence.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentDaoImpl extends CrudDaoImpl<StudentEntity, Long> implements StudentDao {
    private final StudentRepository studentRepository;

    protected StudentDaoImpl(StudentRepository studentRepository) {
        super(studentRepository);
        this.studentRepository = studentRepository;
    }


    @Override
    public StudentEntity getStudentEntityByAppUserEntity(AppUserEntity appUserEntity) {
        return studentRepository.getStudentEntityByAppUserEntity(appUserEntity);
    }
}
