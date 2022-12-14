package io.electrosalaf.reward_your_teacher.usecase.services.impl;

import io.electrosalaf.reward_your_teacher.domain.dao.AppUserDao;
import io.electrosalaf.reward_your_teacher.domain.dao.SchoolDao;
import io.electrosalaf.reward_your_teacher.domain.dao.StudentDao;
import io.electrosalaf.reward_your_teacher.domain.dao.TeacherDao;
import io.electrosalaf.reward_your_teacher.domain.entities.AppUserEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.SchoolEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.enums.Role;
import io.electrosalaf.reward_your_teacher.infrastructure.configuration.security.UserDetails;
import io.electrosalaf.reward_your_teacher.infrastructure.error_handler.CustomNotFoundException;
import io.electrosalaf.reward_your_teacher.infrastructure.error_handler.EntityAlreadyExistException;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.StudentProfileRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.TeacherProfileRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.EditProfileResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.ViewTeacherProfileResponse;
import io.electrosalaf.reward_your_teacher.usecase.services.ProfileService;
import io.electrosalaf.reward_your_teacher.utils.CloudinaryService;
import io.electrosalaf.reward_your_teacher.utils.PayLoadMapper;
import io.electrosalaf.reward_your_teacher.utils.RequestMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final StudentDao studentDao;
    private final SchoolDao schoolDao;
    private final AppUserDao appUserDao;
    private final TeacherDao teacherDao;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;
    private final RequestMapper requestMapper;
    private final PayLoadMapper payLoadMapper;

    @Override
    public EditProfileResponse editStudentProfile(StudentProfileRequest studentProfileRequest) {
        String email = UserDetails.getLoggedInUserDetails();

        SchoolEntity school = schoolDao.findSchool(studentProfileRequest.getNameOfSchool())
                .orElseThrow(() -> new CustomNotFoundException("School not found"));

        AppUserEntity student = appUserDao.findAppUserEntityByEmailAndRole(email,Role.STUDENT);
        if(student == null){
           throw new CustomNotFoundException("student not found");
        }
        AppUserEntity appUserEntity1 = appUserDao.findAppUserEntityByEmail(studentProfileRequest.getEmail());
        if(appUserEntity1 != null){
            throw new EntityAlreadyExistException("Email already exist");
        }
        student.setEmail(studentProfileRequest.getEmail());
        AppUserEntity appUserEntity = appUserDao.saveRecord(student);

        StudentEntity studentEntity = studentDao.getStudentEntityByAppUserEntity(appUserEntity);
        studentEntity.setSchool(school);
        studentEntity.setName(studentProfileRequest.getName());
        studentEntity.setPhoneNumber(studentProfileRequest.getPhone());
        EditProfileResponse editProfileResponse = payLoadMapper.studentEditMapper(studentDao.saveRecord(studentEntity));

        return editProfileResponse;
    }

    @Override
    public EditProfileResponse editTeacherProfile(TeacherProfileRequest teacherProfileRequest, MultipartFile file) throws IOException {
        String email = UserDetails.getLoggedInUserDetails();


        SchoolEntity school = schoolDao.findSchool(teacherProfileRequest.getSchoolTaught())
                .orElseThrow(() -> new CustomNotFoundException("School not found"));

        AppUserEntity appUserEntity = appUserDao.findAppUserEntityByEmailAndRole(email,Role.TEACHER);
        if(appUserEntity == null){
            throw new CustomNotFoundException("Teacher not found");
        }
        AppUserEntity appUserEntity1 = appUserDao.findAppUserEntityByEmail(teacherProfileRequest.getEmail());
        if(appUserEntity1 != null){
            throw new EntityAlreadyExistException("Email already exist");
        }
        appUserEntity.setEmail(teacherProfileRequest.getEmail());
        appUserEntity.setPassword(passwordEncoder.encode(teacherProfileRequest.getPassword()));

        String url = cloudinaryService.uploadImage(file);
        TeacherEntity teacher = teacherDao.getTeacherEntityByAppUserEntity(appUserEntity);
        teacher.setName(teacherProfileRequest.getName());
        teacher.setSchool(school);
        teacher.setYearsOfTeaching(teacherProfileRequest.getYearsOfTeaching());
        teacher.setPhoneNumber(teacherProfileRequest.getPhone());
        teacher.setNin(url);

        EditProfileResponse editProfileResponse = payLoadMapper.TeacherEditMapper( teacherDao.saveRecord(teacher));
        return editProfileResponse;
    }

    @Override
    public List<ViewTeacherProfileResponse> viewTeacherByName(String name) {
        List<TeacherEntity> teachers = teacherDao.findTeacherEntitiesByName(name).orElseThrow(()-> new CustomNotFoundException("Teacher not found"));
        List<ViewTeacherProfileResponse> viewTeacherProfileResponses = new ArrayList<>();
        teachers.forEach(teacher -> {
            ViewTeacherProfileResponse viewTeacherProfileResponse = requestMapper
                    .viewTeacherProfileResponseToTeacherEntityMapper(teacher);
            viewTeacherProfileResponses.add(viewTeacherProfileResponse);
        });
        return viewTeacherProfileResponses;
    }
}