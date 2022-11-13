package io.electrosalaf.reward_your_teacher.usecase.services;

import io.electrosalaf.reward_your_teacher.usecase.payload.request.StudentProfileRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.TeacherProfileRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.EditProfileResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.ViewTeacherProfileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProfileService {
   EditProfileResponse editStudentProfile(StudentProfileRequest studentProfileRequest);
   EditProfileResponse editTeacherProfile(TeacherProfileRequest teacherProfileRequest, MultipartFile file) throws IOException;
   List<ViewTeacherProfileResponse> viewTeacherByName(String name);
}
