package io.electrosalaf.reward_your_teacher.utils;

import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.TeacherSearchResponse;
import org.springframework.stereotype.Service;

@Service
public class ResponseMapper {

    public TeacherSearchResponse teacherSearchResponseToTeacherEntityMapper(TeacherEntity teacher){
        TeacherSearchResponse teacherSearchResponse = new TeacherSearchResponse();
        if(teacher.getName() != null){
            teacherSearchResponse.setName(teacher.getName());
        }
        if(teacher.getSchool() != null){
            teacherSearchResponse.setSchool(teacher.getSchool().getSchoolName());
        }
        if(teacher.getYearsOfTeaching() != null){
            teacherSearchResponse.setYearsOfTeaching(teacher.getYearsOfTeaching());
        }
        return teacherSearchResponse;
    }

}
