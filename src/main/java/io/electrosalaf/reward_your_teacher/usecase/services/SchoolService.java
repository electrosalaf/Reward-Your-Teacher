package io.electrosalaf.reward_your_teacher.usecase.services;

import io.electrosalaf.reward_your_teacher.domain.entities.SchoolEntity;

import java.util.List;

public interface SchoolService {

    String saveSchool(List<SchoolEntity> schoolEntities);

    int getSchoolCount();
}
