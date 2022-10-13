package io.electrosalaf.reward_your_teacher.infrastructure.controllers;

import io.electrosalaf.reward_your_teacher.usecase.payload.request.StudentProfileRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.TeacherProfileRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.ApiResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.EditProfileResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.ViewTeacherProfileResponse;
import io.electrosalaf.reward_your_teacher.usecase.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService studentProfileService;
    private final ProfileService profileService;
    @PostMapping("/edit/student")
    public ResponseEntity<ApiResponse<EditProfileResponse>> editStudentProfile(@Valid @RequestBody StudentProfileRequest studentProfileRequest){
       EditProfileResponse editProfileResponse = studentProfileService.editStudentProfile(studentProfileRequest);
       return new ResponseEntity<>(new ApiResponse<>("Edited successfully",true,editProfileResponse),HttpStatus.OK);
    }

    @PostMapping("/edit/teacher")
    public ResponseEntity<ApiResponse<EditProfileResponse>> editTeacherProfile(@Valid TeacherProfileRequest teacherProfileRequest, MultipartFile file) throws IOException {
       EditProfileResponse editProfileResponse =  profileService.editTeacherProfile(teacherProfileRequest,file);
        return new ResponseEntity<>(new ApiResponse<>("Edited successfully",true,editProfileResponse),HttpStatus.OK);
    }
    @GetMapping("/view/teacher/{teacherName}")
    public ResponseEntity<ApiResponse<List<ViewTeacherProfileResponse>>> viewTeacherProfile(@PathVariable("teacherName") String name) {
        return new ResponseEntity<>(new ApiResponse<>("success",true,profileService.viewTeacherByName(name)), HttpStatus.FOUND);
    }
}
