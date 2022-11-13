package io.electrosalaf.reward_your_teacher.usecase.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherSearchResponse {

    private String name;
    private String school;
    private Integer yearsOfTeaching;
}
