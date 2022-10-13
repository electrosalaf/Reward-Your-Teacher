package io.electrosalaf.reward_your_teacher.infrastructure.controllers;

import io.electrosalaf.reward_your_teacher.usecase.payload.response.ApiResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.SchoolSearchResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.TeacherSearchResponse;
import io.electrosalaf.reward_your_teacher.usecase.services.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/search")
@AllArgsConstructor
public class SearchController {

    private final SearchService service;

    @GetMapping("/schools")
    public ResponseEntity<ApiResponse<Set<SchoolSearchResponse>>> getAllSchools(@RequestParam("offset") int offset, @RequestParam("pageSize") int pageSize) {
        return new ResponseEntity(new ApiResponse<>("success",true,service.findAllSchools(offset, pageSize)), HttpStatus.FOUND);
    }
    @GetMapping("/teachers/{schoolName}")
    public ResponseEntity<ApiResponse<List<TeacherSearchResponse>>> findAllTeacherInASchool(
            @RequestParam("offset") int offset,
            @RequestParam("pageSize") int pageSize,
            @PathVariable("schoolName") String schoolName
    ) {
        return new ResponseEntity<>(new ApiResponse<>("success",true,service.findAllTeachersInASchool(offset, pageSize, schoolName)), HttpStatus.FOUND);
    }

    @GetMapping("/teachers")
    public ResponseEntity<ApiResponse<List<TeacherSearchResponse>>> getAllTeachers(@RequestParam("offset") int offset, @RequestParam("pageSize") int pageSize) {
        return new ResponseEntity<>(new ApiResponse<>("success",true,service.getAllTeachers(offset, pageSize)), HttpStatus.FOUND);
    }

    @GetMapping("/teacher/{teacherName}")
    public ResponseEntity<ApiResponse<List<TeacherSearchResponse>>> findTeacher(@PathVariable("teacherName") String keyword) {
        return new ResponseEntity<>(new ApiResponse<>("success",true,service.searchTeacherByName(keyword)), HttpStatus.FOUND);
    }
}


