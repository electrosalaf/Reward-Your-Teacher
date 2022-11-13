package io.electrosalaf.reward_your_teacher.usecase.payload.response;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RegistrationResponse<T> {
    private String name;
    private String email;
    private long id;
}
