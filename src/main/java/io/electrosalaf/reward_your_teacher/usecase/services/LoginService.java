package io.electrosalaf.reward_your_teacher.usecase.services;

import io.electrosalaf.reward_your_teacher.usecase.payload.request.LoginRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.request.SocialLoginRequest;
import io.electrosalaf.reward_your_teacher.usecase.payload.response.LoginResponse;

public interface LoginService {
    LoginResponse loginStudent(LoginRequest loginRequest);

    LoginResponse studentSocialLogin(SocialLoginRequest socialLoginRequest);

    LoginResponse loginTeacher(LoginRequest loginRequest);

    LoginResponse teacherSocialLogin(SocialLoginRequest socialLoginRequest);
}
