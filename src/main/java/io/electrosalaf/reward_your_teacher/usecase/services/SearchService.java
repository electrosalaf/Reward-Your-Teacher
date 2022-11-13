package io.electrosalaf.reward_your_teacher.usecase.services;

import io.electrosalaf.reward_your_teacher.usecase.payload.response.SchoolSearchResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.TeacherSearchResponse;

import java.util.List;
import java.util.Set;

public interface SearchService {
    Set<SchoolSearchResponse> findAllSchools(int offSet, int pageSize);
    List<TeacherSearchResponse> findAllTeachersInASchool(int offset, int pageSize, String school);

    List<TeacherSearchResponse> getAllTeachers(int offset, int pageSize);

    List<TeacherSearchResponse> searchTeacherByName(String keyword);
}
