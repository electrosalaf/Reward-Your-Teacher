package io.electrosalaf.reward_your_teacher.usecase.services.impl;

import io.electrosalaf.reward_your_teacher.domain.dao.SchoolDao;
import io.electrosalaf.reward_your_teacher.domain.entities.SchoolEntity;
import io.electrosalaf.reward_your_teacher.usecase.services.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SchoolServiceImpl implements SchoolService {

    private final SchoolDao schoolDao;

    @Override
    public String saveSchool(List<SchoolEntity> schoolEntities) {
        schoolDao.saveAllRecord(schoolEntities);
        return "success";
    }

    @Override
    public int getSchoolCount() {
        return schoolDao.getAllRecords().size();
    }

}
