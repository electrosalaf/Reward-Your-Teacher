package io.electrosalaf.reward_your_teacher.domain.entities.message;

import io.electrosalaf.reward_your_teacher.domain.entities.AbstractEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.StudentEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.TeacherEntity;
import io.electrosalaf.reward_your_teacher.domain.entities.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "notification")

public class NotificationEntity extends AbstractEntity {

    @Column(
            name = "message",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @JsonBackReference
    @ManyToOne
    private StudentEntity student;

    @JsonBackReference
    @ManyToOne
    private TeacherEntity teacher;
}
