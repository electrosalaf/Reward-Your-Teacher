package io.electrosalaf.reward_your_teacher.infrastructure.controllers;

import io.electrosalaf.reward_your_teacher.usecase.payload.response.ApiResponse;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.NotificationResponse;
import io.electrosalaf.reward_your_teacher.usecase.services.impl.NotificationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class AppreciationController {

    private final NotificationServiceImpl notificationService;

    @PostMapping("/appreciate-student/{studentId}/{teacherId}")
    public ResponseEntity<ApiResponse<NotificationResponse>> teacherAppreciateStudent(@PathVariable ( value= "studentId")Long studentId, @PathVariable(value = "teacherId") Long teacherId){
            return ResponseEntity.ok(new ApiResponse<>("Success",true,notificationService.studentAppreciatedNotification(studentId, teacherId)));
    }
}

